package sery.vlasenko.movies.model.repository

import io.reactivex.rxjava3.core.Single
import sery.vlasenko.movies.model.pojo.Movie
import sery.vlasenko.movies.model.pojo.Response

interface IMovieRepository {
    fun getAllMovieReviews(offset: Int): Single<Response<Movie>>
}