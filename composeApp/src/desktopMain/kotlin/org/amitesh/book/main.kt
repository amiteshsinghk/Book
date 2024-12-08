package org.amitesh.book

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.amitesh.book.app.App
import org.amitesh.book.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Book",
        ) {
            App()
        }
    }
}