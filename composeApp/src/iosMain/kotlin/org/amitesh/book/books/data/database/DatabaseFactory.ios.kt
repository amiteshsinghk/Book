@file:OptIn(ExperimentalForeignApi::class)

package org.amitesh.book.books.data.database

import androidx.room.RoomDatabase
import androidx.room.Room
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavouriteBookDb> {
        val dbFile = documentDirectory() + "/${FavouriteBookDb.DATABASE_NAME}"
        return Room.databaseBuilder<FavouriteBookDb>(
            name = dbFile
        )
    }

    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentDirectory?.path)
    }
}