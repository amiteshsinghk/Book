package org.amitesh.book.books.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
            observeFavouriteStatus()
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
                viewModelScope.launch {
                   if (state.value.isFavorite){
                       bookRepository.deleteFromFavourites(bookId)
                   } else {
                       println("FavouriteBook:: BookDetailViewModel :: ${state.value.book}")
                       state.value.book?.let { bookRepository.markAsFavourite(it) }
                   }
                }
            }

            else -> Unit
        }
    }

    private fun observeFavouriteStatus(){
        bookRepository.isBookFavourite(bookId)
            .onEach {favourite ->
                _state.update {
                    it.copy(isFavorite = favourite)
                }
            }
            .launchIn(viewModelScope)
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