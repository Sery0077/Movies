package sery.vlasenko.movies.presentation.listMovies.adapter

import com.bumptech.glide.Glide
import sery.vlasenko.movies.R
import sery.vlasenko.movies.databinding.ItemMovieBinding
import sery.vlasenko.movies.model.pojo.Movie
import sery.vlasenko.movies.presentation.base.adapter.BaseAdapter
import sery.vlasenko.movies.presentation.base.adapter.RecyclerItem

class MovieVH(private val binding: ItemMovieBinding) : BaseAdapter.BaseVH(binding) {
    override fun bind(item: RecyclerItem?) {
        item as Movie
        with(binding) {
            tvMovieTitle.text = item.displayTitle
            tvMovieDescription.text = item.summaryShot

            Glide.with(binding.root)
                .load(item.multimedia.imageURL)
                .placeholder(R.drawable.movie_placeholder)
                .into(ivMoviePreview)
        }
    }
}