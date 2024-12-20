package org.amitesh.book.books.domain.repository

import kotlinx.coroutines.flow.Flow
import org.amitesh.book.books.domain.Book
import org.amitesh.book.core.domain.DataError
import org.amitesh.book.core.domain.EmptyResult
import org.amitesh.book.core.domain.Result

// Here we don't have any import of data layer
// presentation -> domain <- data
interface BookRepository {
    suspend fun searchBooks(
        query: String,
        resultLimit:Int? = null
    ): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId:String): Result<String?, DataError>

    suspend fun getFavouriteBooks(): Flow<List<Book>>
    fun isBookFavourite(bookId: String): Flow<Boolean>
    suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavourites(id: String)
}