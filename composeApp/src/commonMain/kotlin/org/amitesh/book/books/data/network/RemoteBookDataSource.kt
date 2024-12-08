package org.amitesh.book.books.data.network

import org.amitesh.book.books.data.dto.BookWorkDto
import org.amitesh.book.books.data.dto.SearchResponseDto
import org.amitesh.book.core.domain.DataError
import org.amitesh.book.core.domain.Result

// presentation -> domain <- data
// we don't have to call data layer from presentation layer it will violate the principal of clean architecture.
interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit:Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookId:String):Result<BookWorkDto, DataError.Remote>
}