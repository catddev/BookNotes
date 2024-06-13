package com.epamupskills.booknotes.booknotes.presentation.notes

import androidx.lifecycle.viewModelScope
import com.epamupskills.booknotes.booknotes.domain.interactors.NoteInteractor
import com.epamupskills.booknotes.core.Constants.EMPTY
import com.epamupskills.booknotes.core.NavigateUp
import com.epamupskills.booknotes.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = NoteViewModel.Factory::class)
class NoteViewModel @AssistedInject constructor(
    @Assisted private val bookId: String,
    private val interactor: NoteInteractor,
) : BaseViewModel() {

    private val _state = MutableStateFlow(NoteViewState())
    val state = _state.asStateFlow()

    init {
        getInitialNote()
        onNoteChanged()
        onBookDeleted()
    }

    fun onIntent(intent: NoteUserIntent) {
        when (intent) {
            is EditNote -> onEditNote(intent.content)
            is ClearNote -> onClearNote()
        }
    }

    private fun onEditNote(noteEdited: String) {
        _state.update { it.copy(note = noteEdited) }
    }

    private fun onClearNote() = viewModelScope.launch {
        _state.update { it.copy(note = EMPTY) }
    }

    private fun onBookDeleted() {
        viewModelScope.launch {
            interactor.checkBookIsSaved(bookId).onSuccess { result ->
                result.collect { doesBookExist ->
                    if (!doesBookExist) onNavigationEvent(NavigateUp())
                }
            }.renderBaseStateByResult()
        }
    }

    @OptIn(FlowPreview::class)
    private fun onNoteChanged() {
        launchCatching {
            _state
                .debounce(EDIT_DELAY)
                .distinctUntilChanged()
                .collectLatest { state ->
                    interactor.updateNote(note = state.note, bookId = bookId)
                }
        }
    }

    private fun getInitialNote() {
        viewModelScope.launch {
            interactor.getNote(bookId).onSuccess { note ->
                _state.value = _state.value.copy(note = note)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(bookId: String): NoteViewModel
    }

    companion object {
        private const val EDIT_DELAY = 300L
    }
}