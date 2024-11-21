package org.amitesh.book.books.presentation.book_list

import org.amitesh.book.books.domain.Book

sealed interface BookListAction {
    data class  OnSearchQueryChange(val query: String): BookListAction
    data class OnBookClick(val book: Book): BookListAction
    data class OnTabSelected(val index: Int): BookListAction
}