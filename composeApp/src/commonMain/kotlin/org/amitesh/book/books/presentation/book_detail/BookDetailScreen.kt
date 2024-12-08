package org.amitesh.book.books.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import book.composeapp.generated.resources.Res
import book.composeapp.generated.resources.description_not_available
import book.composeapp.generated.resources.languages
import book.composeapp.generated.resources.pages
import book.composeapp.generated.resources.ratings
import book.composeapp.generated.resources.synopsis
import org.amitesh.book.books.presentation.book_detail.component.BlurredImageBackground
import org.amitesh.book.books.presentation.book_detail.component.BookChip
import org.amitesh.book.books.presentation.book_detail.component.ChipSize
import org.amitesh.book.books.presentation.book_detail.component.TitledContent
import org.amitesh.book.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel, onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BookDetailScreen(state = state, onAction = { action ->
        when (action) {
            is BookDetailAction.OnBackClick -> onBackClick()
            else -> Unit
        }
        viewModel.onAction(action)
    })

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
) {
    BlurredImageBackground(
        imageUrl = state.book?.imageUrl,
        isFavorite = state.isFavorite,
        onFavoriteClick = { onAction(BookDetailAction.OnFavoriteClick) },
        onBackClick = { onAction(BookDetailAction.OnBackClick) },
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.book != null) {
            Column(
                modifier = Modifier.widthIn(max = 700.dp).fillMaxWidth().padding(
                        vertical = 16.dp, horizontal = 16.dp
                    ).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.book.title ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = state.book.authors?.joinToString() ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )

                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    println("averageRating :: ${state.book.averageRating}")

                    state.book.averageRating?.let { rating ->
                        TitledContent(
                            title = stringResource(Res.string.ratings)
                        ) {

                            BookChip {
                                Row {
                                    Text(
                                        text = "${round(rating * 10) / 10.0}"
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        modifier = Modifier.padding(start = 4.dp),
                                        tint = SandYellow
                                    )
                                }
                            }
                        }
                    }

                    state.book.numPages?.let { pages ->
                        TitledContent(
                            title = stringResource(Res.string.pages)
                        ) {
                            BookChip {
                                Text(
                                    text = pages.toString()
                                )
                            }
                        }

                    }

                }

                if (state.book.languages.isNotEmpty()) {
                    TitledContent(
                        title = stringResource(Res.string.languages),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        ) {
                            state.book.languages.forEach {
                                BookChip(
                                    size = ChipSize.SMALL, modifier = Modifier.padding(2.dp)
                                ) {
                                    Text(
                                        text = it.uppercase(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Start).fillMaxSize().padding(
                            top = 26.dp, bottom = 16.dp
                        )
                )
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = if (state.book.description != null) {
                            state.book.description
                        } else {
                            stringResource(Res.string.description_not_available)
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        color = if (state.book.description.isNullOrBlank()) {
                            Color.Black.copy(alpha = 0.4f)
                        } else Color.Black,
                        modifier = Modifier.padding(
                                vertical = 8.dp
                            )
                    )
                }


            }
        }
    }
}