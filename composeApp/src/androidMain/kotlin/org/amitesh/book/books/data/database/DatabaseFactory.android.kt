package org.amitesh.book.books.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<FavouriteBookDb> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(FavouriteBookDb.DATABASE_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }

}