package com.epamupskills.book_notes.presentation.notes

import androidx.lifecycle.viewModelScope
import com.epamupskills.book_notes.domain.interactors.NoteInteractor
import com.epamupskills.book_notes.presentation.mappers.NoteFromUiMapper
import com.epamupskills.book_notes.presentation.mappers.NoteToUiMapper
import com.epamupskills.book_notes.presentation.models.NoteUi
import com.epamupskills.core.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = NoteViewModel.Factory::class)
class NoteViewModel @AssistedInject constructor(
    @Assisted private val id: Long?,
    private val interactor: NoteInteractor,
    private val noteToUiMapper: NoteToUiMapper,
    private val noteFromUiMapper: NoteFromUiMapper,
) : BaseViewModel() {

    private val noteId = MutableStateFlow(id)
    private val _state = MutableStateFlow(NoteUi(null, ""))
    val state = _state.asStateFlow()

    init {
        onNoteIdChanged()
    }

    fun onIntent(intent: NoteUserIntent) {
        when (intent) {
            is EditNote -> onEditNote(intent.content) //todo by click?
            is ClearNote -> onClearNote()
        }
    }

    private fun onEditNote(content: String) { //todo refactor!!! - double tap + debounce {} - check on search
        val editedNote = noteFromUiMapper.transform(state.value.copy(content = content))

        viewModelScope.launch {
            when (noteId.value) {
                null -> interactor.createNote(editedNote).onSuccess {
                    noteId.value = it
                }

                else -> interactor.updateNote(editedNote)
            }.renderBaseStateByResult()
        } //todo do not redirect
    }


    private fun onClearNote() = viewModelScope.launch {
        when (val id = noteId.value) {
            null -> _state.update { it.copy(content = "") }

            else -> interactor.removeNote(id).onSuccess {
                //todo when cleared, also clear noteId for BookEntity, check booklist changes header
                //todo emit noteId.value = null -> .also { noteId.value = null }
            }.renderBaseStateByResult()
        }
    }

    private fun onNoteIdChanged() {
        noteId.filterNotNull().onEach { id ->
            interactor.getNote(id).onSuccess { result ->
                result.collect { note -> //todo check is observed till id changes
                    _state.value = when (note) {
                        null -> {
                            _state.value.copy(noteId = null, content = "")
                            //todo check bookId is null and popBackStack

                            //todo should redirect from NoteFragment: only for landscape to placeholder
                            //todo means when popbackstack is not null?
                        }
                        else -> noteToUiMapper.transform(note)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long?): NoteViewModel
    }
}