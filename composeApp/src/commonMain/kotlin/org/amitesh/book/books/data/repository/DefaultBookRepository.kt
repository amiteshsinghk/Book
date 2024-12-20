package org.amitesh.book.books.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.amitesh.book.books.data.database.FavouriteBookDao
import org.amitesh.book.books.data.mappers.toBook
import org.amitesh.book.books.data.mappers.toBookEntity
import org.amitesh.book.books.data.network.RemoteBookDataSource
import org.amitesh.book.books.domain.Book
import org.amitesh.book.books.domain.repository.BookRepository
import org.amitesh.book.core.domain.DataError
import org.amitesh.book.core.domain.EmptyResult
import org.amitesh.book.core.domain.Result
import org.amitesh.book.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favouriteBookDao: FavouriteBookDao
) : BookRepository {
    override suspend fun searchBooks(
        query: String, resultLimit: Int?
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query, resultLimit).map {searchResponseDto->
            searchResponseDto.docs.map {
                it.toBook()
            }
        }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        val localResult = favouriteBookDao.getFavoriteBook(bookId)
        return if (localResult == null){
            remoteBookDataSource.getBookDetails(bookId).map { it.description }
        }else Result.Success(localResult.description)
    }

    override suspend fun getFavouriteBooks(): Flow<List<Book>> {
        return favouriteBookDao.getAllBooks().map {favouriteBook->
            favouriteBook.map { it.toBook() }
        }
    }

    override fun isBookFavourite(bookId: String): Flow<Boolean> {
        return favouriteBookDao
            .getAllBooks()
            .map{bookEntity->
                bookEntity.any { it.id == bookId }
            }
    }

    override suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local> {
        return  try {
            println("FavouriteBook:: markAsFavourite :: $book")
            favouriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        }catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavourites(id: String) {
        favouriteBookDao.deleteFavoriteBook(id)
    }
}