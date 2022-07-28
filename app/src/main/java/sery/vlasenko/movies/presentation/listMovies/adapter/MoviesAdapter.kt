package sery.vlasenko.movies.presentation.listMovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import sery.vlasenko.movies.databinding.ItemLoadingBinding
import sery.vlasenko.movies.databinding.ItemMovieBinding
import sery.vlasenko.movies.presentation.base.adapter.BaseAdapter
import sery.vlasenko.movies.presentation.base.adapter.LoadingVH


class MoviesAdapter : BaseAdapter() {

    companion object {
        const val ITEM = 0
        const val LOADING = 1
    }

    override fun getItemViewType(position: Int): Int =
        if (currentList[position] == null) LOADING else ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return when (viewType) {
            ITEM -> {
                val binding =
                    ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieVH(binding)
            }
            LOADING -> {
                val binding =
                    ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingVH(binding)
            }
            else -> throw IllegalStateException("Unknown item type: $viewType")
        }
    }
}