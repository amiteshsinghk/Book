package org.amitesh.book.books.data.mappers

import org.amitesh.book.books.data.dto.SearchBookDto
import org.amitesh.book.books.domain.Book

fun SearchBookDto.toBook(): Book {
    println("SearchBookDto :: averageRating $ratingAverage :: ratingCount $ratingCount")
    return Book(
        id = id.substringAfterLast("/"),
        title = title ?: "",
        imageUrl = "https://covers.openlibrary.org/b/olid/${coverKey ?: coverAlternativeKey}-L.jpg",
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear?.toString(),
        averageRating = ratingAverage,
        ratingCount = ratingCount,
        numPages = numberOfPagesMedian,
        numEditions = numEditionCount ?: 0,
    )
}