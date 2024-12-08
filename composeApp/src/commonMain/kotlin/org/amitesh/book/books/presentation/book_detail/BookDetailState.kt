package org.amitesh.book.books.presentation.book_detail

import org.amitesh.book.books.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)