package org.amitesh.book.books.data.repository

import org.amitesh.book.books.data.mappers.toBook
import org.amitesh.book.books.data.network.RemoteBookDataSource
import org.amitesh.book.books.domain.Book
import org.amitesh.book.books.domain.repository.BookRepository
import org.amitesh.book.core.domain.DataError
import org.amitesh.book.core.domain.Result
import org.amitesh.book.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {
    override suspend fun searchBooks(
        query: String, resultLimit: Int?
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query, resultLimit).map {
            it.docs.map {
                it.toBook()
            }
        }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        return remoteBookDataSource.getBookDetails(bookId).map { it.description }
    }
}