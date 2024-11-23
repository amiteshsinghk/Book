package org.amitesh.book.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.amitesh.book.books.presentation.book_list.BookListScreenRoot
import org.amitesh.book.books.presentation.book_list.BookListViewModel
import org.amitesh.book.books.presentation.book_list.SelectedViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme{
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.BookGraph
        ){
            navigation<Routes.BookGraph>(
                startDestination = Routes.BookList
            ){
                composable<Routes.BookList> {
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedViewModel>(navController)
                    LaunchedEffect(true){
                        selectedBookViewModel.onSelectedBook(null)
                    }
                    BookListScreenRoot(
                        viewModel = viewModel,
                        onBookClick = {
                            selectedBookViewModel.onSelectedBook(it)
                            navController.navigate(Routes.BookDetail(it.id))
                        }
                    )
                }
                
                composable<Routes.BookDetail> { entry ->
                    val selectedBookViewModel =
                        entry.sharedKoinViewModel<SelectedViewModel>(navController)
                    val selectedBook = selectedBookViewModel.selected.collectAsStateWithLifecycle()
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text("Book Detail Screen id: ${selectedBook.value}")
                    }

                }
            }
        }

    }
}


// Shared ViewModel in compose
@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry  = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}