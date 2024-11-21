package org.amitesh.book

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.amitesh.book.books.presentation.book_list.BookListScreenRoot
import org.amitesh.book.books.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    BookListScreenRoot(
        viewModel = remember {
            BookListViewModel()
        }, onBookClick = {

        }
    )
}