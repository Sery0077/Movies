package sery.vlasenko.movies.presentation.listMovies

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import sery.vlasenko.movies.databinding.ActivityListMoviesBinding
import sery.vlasenko.movies.model.pojo.Movie
import sery.vlasenko.movies.presentation.listMovies.adapter.MoviesAdapter
import sery.vlasenko.movies.utils.SnackBarHelper
import sery.vlasenko.movies.utils.gone
import sery.vlasenko.movies.utils.visible

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMoviesBinding
    private val model: MoviesViewModel by viewModels()

    private var errorSnackBar: Snackbar? = null
    private val adapter: MoviesAdapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListMoviesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initRecycler()

        model.state.observe(this) {
            processState(it)
        }

        model.movies.observe(this) {
            processData(it)
        }
    }

    private fun processState(state: MoviesState) {
        when (state) {
            is MoviesState.DataLoadError -> {
                binding.progressBar.gone()
                Toast.makeText(this, state.error, Toast.LENGTH_SHORT).show()
                errorSnackBar = SnackBarHelper.errorSnackBar(binding.root) {
                    model.onErrorClick()
                }
                errorSnackBar?.show()
            }
            MoviesState.DataLoaded -> {
                errorSnackBar?.dismiss()
                binding.progressBar.gone()
                binding.rvMovies.visible()
            }
            MoviesState.DataLoading -> {
                errorSnackBar?.dismiss()
                binding.progressBar.visible()
                binding.rvMovies.gone()
            }
        }
    }

    private fun processData(movies: List<Movie?>) {
        adapter.submitList(movies)
    }

    private fun initRecycler() {
        with(binding.rvMovies) {
            setHasFixedSize(true)
            adapter = this@MoviesActivity.adapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!recyclerView.canScrollVertically(1)) {
                        model.onEndScroll()
                    }
                }
            })
        }
    }
}