package org.amitesh.book

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.amitesh.book.books.domain.Book
import org.amitesh.book.books.presentation.book_detail.component.BookChip
import org.amitesh.book.books.presentation.book_detail.component.ChipSize
import org.amitesh.book.books.presentation.book_detail.component.TitledContent
import org.amitesh.book.books.presentation.book_list.BookListScreen
import org.amitesh.book.books.presentation.book_list.BookListState


private val books =(1..100).map{
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://picsum.photos/200",
        authors = listOf("Author $it"),
        description = "Description $it",
        languages = listOf("English"),
        firstPublishYear = "2020",
        averageRating = 4.5,
        ratingCount = 100,
        numPages = 100,
        numEditions = 1
    )
}

val b = Book(
    id = 1.toString(),
    title = "Book 1",
    imageUrl = "https://picsum.photos/200",
    authors = listOf("Author 1"),
    description = "Description 1t",
    languages = listOf("English"),
    firstPublishYear = "2020",
    averageRating = 4.5,
    ratingCount = 100,
    numPages = 100,
    numEditions = 1
)


//@Preview
//@Composable
//fun BookListItemsPreview() {
//    BookListScreen(
//        state = BookListState(
//            searchResults = books,
// favouriteBooks = emptyList(),
//            searchQuery = "Book",
//            isLoading = books.isEmpty(),
//            selectedTabIndex = 1,
//            errorMessage = null,
//
//        ),
//        onAction = {}
//
//    )
//}

@Preview
@Composable
fun BookChips(){
    BookChip(
        modifier = Modifier,
        size = ChipSize.SMALL,
        chipComponent = {
            TitledContent(title = "Abc",
            modifier = Modifier,
            content = {})
        }
    )
}
