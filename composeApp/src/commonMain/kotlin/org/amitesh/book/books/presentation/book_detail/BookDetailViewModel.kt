package org.amitesh.book.books.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.amitesh.book.app.Routes
import org.amitesh.book.books.domain.repository.BookRepository
import org.amitesh.book.core.domain.onSuccess

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val bookId = saveStateHandle.toRoute<Routes.BookDetail>().id
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            getBookDescription()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value

        )

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.OnSelectedBookChange -> {
                _state.value = _state.value.copy(
                    book = action.book
                )
            }

            is BookDetailAction.OnFavoriteClick -> {

            }

            else -> Unit
        }
    }

    private fun getBookDescription() {
        viewModelScope.launch {
            bookRepository.getBookDescription(bookId)
                .onSuccess { item ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            book = it.book?.copy(description = item)
                        )
                    }
                }

        }
    }
}