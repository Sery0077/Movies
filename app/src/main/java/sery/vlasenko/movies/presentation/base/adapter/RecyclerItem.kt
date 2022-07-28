package sery.vlasenko.movies.presentation.base.adapter

interface RecyclerItem {
    val id: String?
    override fun equals(other: Any?): Boolean
}