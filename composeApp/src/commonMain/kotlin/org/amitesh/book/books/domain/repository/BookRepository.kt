package org.amitesh.book.books.domain.repository

import org.amitesh.book.books.domain.Book
import org.amitesh.book.core.domain.DataError
import org.amitesh.book.core.domain.Result

// Here we don't have any import of data layer
// presentation -> domain <- data
interface BookRepository {
    suspend fun searchBooks(
        query: String,
        resultLimit:Int? = null
    ): Result<List<Book>, DataError.Remote>
}