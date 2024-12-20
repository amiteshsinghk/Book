package org.amitesh.book.books.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BookEntity::class],
    version = 1
)

@TypeConverters(
    StringListTypeConverter::class
)
abstract class FavouriteBookDb: RoomDatabase() {
    abstract val dao: FavouriteBookDao

    companion object{
        const val DATABASE_NAME = "favourite_book.db"
    }

}