package org.amitesh.book.books.presentation.book_detail

import org.amitesh.book.books.domain.Book

interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data object OnFavoriteClick: BookDetailAction
    data class OnSelectedBookChange(val book: Book): BookDetailAction
}

