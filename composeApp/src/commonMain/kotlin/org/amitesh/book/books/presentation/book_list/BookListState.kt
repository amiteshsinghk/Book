package org.amitesh.book.books.presentation.book_list

import org.amitesh.book.books.domain.Book
import org.amitesh.book.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favouriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)

private val books = (1..10000).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://picsum.photos/200",
        authors = listOf("Author $it"),
        description = "Description $it",
        languages = listOf("English"),
        firstPublishYear = "2020",
        averageRating = 4.5,
        ratingCount = 100,
        numPages = 100,
        numEditions = 1
    )
}
