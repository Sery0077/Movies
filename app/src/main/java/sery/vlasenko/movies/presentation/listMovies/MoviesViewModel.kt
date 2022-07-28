package sery.vlasenko.movies.presentation.listMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import sery.vlasenko.movies.model.pojo.Movie
import sery.vlasenko.movies.model.pojo.Response
import sery.vlasenko.movies.model.repository.IMovieRepository
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: IMovieRepository) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val data: ArrayList<Movie?> = arrayListOf(null)

    private val _state: MutableLiveData<MoviesState> = MutableLiveData(MoviesState.DataLoading)
    val state: LiveData<MoviesState> = _state

    private var _movies: MutableLiveData<List<Movie?>> = MutableLiveData()
    val movies: LiveData<List<Movie?>> = _movies

    private var hasNextPage = true
    private var maxPage = 0

    init {
        getMovieReviews()
    }

    private fun getMovieReviews() {
        if (data.size == 1) _state.value = MoviesState.DataLoading
        if (hasNextPage) {
            disposable.add(repository.getAllMovieReviews(maxPage * 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        onSuccess(it)
                    },
                    onError = {
                        onError(it)
                    }
                ))
        }
    }

    private fun onSuccess(response: Response<Movie>) {
        data.apply {
            data.removeLast()
            addAll(response.results)
            if (response.hasMore) add(null)
        }

        _movies.value = data
        _state.value = MoviesState.DataLoaded

        if (response.hasMore) maxPage++ else hasNextPage = false
    }


    private fun onError(error: Throwable) {
        _state.value = MoviesState.DataLoadError(error.localizedMessage)
    }

    fun onEndScroll() {
        getMovieReviews()
    }

    fun onErrorClick() {
        getMovieReviews()
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}