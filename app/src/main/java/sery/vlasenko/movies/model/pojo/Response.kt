package sery.vlasenko.movies.model.pojo

import com.squareup.moshi.Json

data class Response<T>(
    val status: String,
    @field:Json(name = "has_more") val hasMore: Boolean,
    @field:Json(name = "results") val results: List<T>,
)
