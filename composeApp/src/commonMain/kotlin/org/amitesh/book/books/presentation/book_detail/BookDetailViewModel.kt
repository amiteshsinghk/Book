package org.amitesh.book.books.presentation.book_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookDetailViewModel: ViewModel() {
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: BookDetailAction){
        when(action){
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
}