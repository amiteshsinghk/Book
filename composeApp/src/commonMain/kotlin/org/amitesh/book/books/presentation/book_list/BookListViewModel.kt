package org.amitesh.book.books.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.amitesh.book.books.domain.Book
import org.amitesh.book.books.domain.repository.BookRepository
import org.amitesh.book.core.domain.onError
import org.amitesh.book.core.domain.onSuccess
import org.amitesh.book.core.presentation.toUiText

//Presentation -> Domain <- Data
// Here we don't have any import of data layer
class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if (cachedBookList.isEmpty()) observeSearchQuery()
        }
        .stateIn(
            viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    private var cachedBookList = emptyList<Book>()
    private var searchJob: Job? = null

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> {

            }

            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is BookListAction.OnTabSelected -> {
                _state.update {
                    println("BookList :: BookListViewModel :: OnTabSelected :: ${action.index}")
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged() // Api will trigger only when there is any change in the searchQuery.
            .debounce(500L) // This is used to check the user has stopped typing  for 500ms than we call the api.
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBookList
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch  {

            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            bookRepository.searchBooks(
                query = query,
            ).onSuccess { searchResult ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResults = searchResult,
                        errorMessage = null
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResults = emptyList(),
                        errorMessage = error.toUiText()
                    )
                }
            }

    }

}