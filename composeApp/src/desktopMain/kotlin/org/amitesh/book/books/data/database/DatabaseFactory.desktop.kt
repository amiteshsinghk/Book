package org.amitesh.book.books.data.database

import androidx.room.RoomDatabase
import androidx.room.Room
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavouriteBookDb> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "Book")
            os.contains("mac") -> File(userHome, "Library/Application Support/Book")
            else -> File(userHome, ".local/share/Book")
        }

        if(!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, FavouriteBookDb.DATABASE_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}