package org.amitesh.book.books.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<FavouriteBookDb>

}