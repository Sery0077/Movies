package sery.vlasenko.movies.model.repository

import io.reactivex.rxjava3.core.Single
import sery.vlasenko.movies.model.MovieService
import sery.vlasenko.movies.model.pojo.Movie
import sery.vlasenko.movies.model.pojo.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val service: MovieService) : IMovieRepository {
    override fun getAllMovieReviews(offset: Int): Single<Response<Movie>> =
        service.getAllMovieReviews(offset = offset)
}