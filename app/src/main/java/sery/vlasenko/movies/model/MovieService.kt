package sery.vlasenko.movies.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import sery.vlasenko.movies.model.pojo.Movie
import sery.vlasenko.movies.model.pojo.Response

interface MovieService {
    @GET("/svc/movies/v2/reviews/{type}")
    fun getAllMovieReviews(
        @Path("type") type: String = "all.json",
        @Query("offset") offset: Int,
    ): Single<Response<Movie>>
}