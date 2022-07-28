package sery.vlasenko.movies.model.pojo

import com.squareup.moshi.Json
import sery.vlasenko.movies.presentation.base.adapter.RecyclerItem

data class Movie(
    @field:Json(name = "headline") override val id: String, // for correct operation of DiffCallback
    @field:Json(name = "display_title") val displayTitle: String,
    @field:Json(name = "summary_short") val summaryShot: String,
    @field:Json(name = "multimedia") val multimedia: Multimedia
) : RecyclerItem

data class Multimedia(
    @field:Json(name = "src") val imageURL: String
)
