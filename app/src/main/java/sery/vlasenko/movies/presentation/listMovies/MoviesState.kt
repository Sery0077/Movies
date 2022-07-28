package sery.vlasenko.movies.presentation.listMovies

sealed class MoviesState {
    object DataLoading : MoviesState()
    object DataLoaded : MoviesState()
    class DataLoadError(val error: String) : MoviesState()
}
