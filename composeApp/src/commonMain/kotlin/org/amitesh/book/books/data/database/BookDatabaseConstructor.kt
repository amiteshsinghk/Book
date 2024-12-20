package org.amitesh.book.books.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor: RoomDatabaseConstructor<FavouriteBookDb> {
    override fun initialize(): FavouriteBookDb

}