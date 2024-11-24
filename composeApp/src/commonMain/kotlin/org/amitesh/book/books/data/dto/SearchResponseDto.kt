package org.amitesh.book.books.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("docs") val docs: List<SearchBookDto>,
    @SerialName("numFound")val numFound: Int,
    @SerialName("numFoundExact")val numFoundExact: Boolean,
    @SerialName("q")val q: String,
    @SerialName("start")val start: Int
)