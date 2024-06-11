package com.epamupskills.book_notes.presentation.notes

import androidx.lifecycle.viewModelScope
import com.epamupskills.book_notes.domain.interactors.NoteInteractor
import com.epamupskills.book_notes.presentation.mappers.NoteFromUiMapper
import com.epamupskills.book_notes.presentation.mappers.NoteToUiMapper
import com.epamupskills.core.NavigateUp
import com.epamupskills.core.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = NoteViewModel.Factory::class)
class NoteViewModel @AssistedInject constructor(
    @Assisted private val id: Long?,
    @Assisted private val bookId: String,
    private val interactor: NoteInteractor,
    private val noteToUiMapper: NoteToUiMapper,
    private val noteFromUiMapper: NoteFromUiMapper,
) : BaseViewModel() {

    private val noteId = MutableStateFlow(id)
    private val _state = MutableStateFlow(NoteViewState())
    val state = _state.asStateFlow()

    init {
        onNoteIdChanged()
        onNoteContentChanged()
    }

    fun onIntent(intent: NoteUserIntent) {
        when (intent) {
            is EditNote -> onEditNote(intent.content)
            is ClearNote -> onClearNote()
        }
    }

    private fun onEditNote(content: String) {
        _state.update { it.copy(note = it.note.copy(content = content)) }
    }


    private fun onClearNote() = viewModelScope.launch {
        noteId.value?.let {
            interactor.removeNote(it).onSuccess {
                noteId.value = null
                interactor.updateBookWithNote(noteId = null, bookId = bookId)
                //todo check book changes header
            }.renderBaseStateByResult()
        } ?: _state.update { state ->
            state.copy(note = state.note.copy(content = ""))
        }
    }

    private fun onNoteIdChanged() {
        noteId
            .filterNotNull()
            .onEach { id ->
                interactor.getNote(id).onSuccess { result ->
                    result.collect { note ->
                        when (note) {
                            null -> {
                                interactor.checkBookExists(id).onSuccess { doesBookExist ->
                                    if (!doesBookExist) onNavigationEvent(NavigateUp)
                                }
                            }

                            else -> _state.update {
                                it.copy(note = noteToUiMapper.transform(note))
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun onNoteContentChanged() {
        _state
            .debounce(EDIT_DELAY)
            .onEach { state ->
                val editedNote = noteFromUiMapper.transform(state.note)

                when { //todo refactor - separate fun for add, update, remove
                    noteId.value != null -> interactor.updateNote(editedNote)

                    editedNote.content.isNotBlank() -> interactor.createNote(editedNote).onSuccess {
                        noteId.value = it
                        interactor.updateBookWithNote(noteId = it, bookId = bookId)
                    }.renderBaseStateByResult()

                    else -> onClearNote()
                }
            }
            .launchIn(viewModelScope)
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long?, bookId: String): NoteViewModel
    }

    companion object {
        private const val EDIT_DELAY = 500L
    }
}