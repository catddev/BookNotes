package com.epamupskills.booknotes.booknotes.presentation.search

import androidx.lifecycle.viewModelScope
import com.epamupskills.booknotes.booknotes.domain.interactors.BookSearchInteractor
import com.epamupskills.booknotes.booknotes.presentation.mappers.BookFromUiMapper
import com.epamupskills.booknotes.booknotes.presentation.mappers.BookToUiMapper
import com.epamupskills.booknotes.booknotes.presentation.models.BookUi
import com.epamupskills.booknotes.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val interactor: BookSearchInteractor,
    private val bookToUiMapper: BookToUiMapper,
    private val bookFromUiMapper: BookFromUiMapper,
) : BaseViewModel() {

    private val user = MutableSharedFlow<Unit>()
    private val searchInput = MutableStateFlow("")
    private val searchResult = MutableStateFlow<List<BookUi>>(emptyList())
    private val _state = MutableStateFlow(BookSearchViewState())
    val state = _state.asStateFlow()

    init {
        onSearchInputChanged()
        onUserChanged()
        onMergeBookmarks()
    }

    fun onIntent(intent: SearchBookUserIntent) {
        when (intent) {
            is Search -> onSearchBook(intent.input)
            is ToggleBookmark -> onToggleBookmark(intent.book)
        }
    }

    private fun onSearchBook(input: String) {
        searchInput.update { input }
    }

    private fun onToggleBookmark(book: BookUi) {
        viewModelScope.launch {
            when {
                book.isBookmarked -> interactor.removeBook(book.id)
                else -> interactor.saveBook(bookFromUiMapper.transform(book))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun onMergeBookmarks() {
        launchCatching {
            user.flatMapLatest {
                interactor.getAllUserBooks().onSuccess { dbBooksFlow ->
                    combine(dbBooksFlow, searchResult) { dbBooks, searchedBooks ->
                        searchedBooks.map { searchBook ->
                            searchBook.copy(isBookmarked = dbBooks.any { it.id == searchBook.id })
                        }
                    }.collect { searchResultWithBookmarks ->
                        _state.update { it.copy(searchResults = searchResultWithBookmarks) }
                    }
                }.getOrThrow()
            }.collect()
        }
    }

    @OptIn(FlowPreview::class)
    private fun onSearchInputChanged() {
        launchCatching {
            searchInput
                .debounce(SEARCH_INPUT_DELAY)
                .map { it.trim() }
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collectLatest { input ->
                    loading.value = true
                    _state.update { it.copy(isKeyboardOpen = false) }

                    interactor.searchBooks(input)
                        .onSuccess { books ->
                            searchResult.value = bookToUiMapper.transformAll(books)
                        }
                        .onFailure { _state.update { it.copy(searchResults = emptyList()) } }
                        .renderBaseStateByResult()
                }
        }
    }

    private fun onUserChanged() { //todo get from activity
        viewModelScope.launch {
            interactor.checkAuth().onSuccess { isAuth ->
                isAuth.collect { isUserAuth ->
                    user.emit(Unit)
                    if (!isUserAuth) _state.update { it.copy(searchResults = emptyList()) }
                }
            }
        }
    }

    companion object {
        private const val SEARCH_INPUT_DELAY = 1000L
    }
}