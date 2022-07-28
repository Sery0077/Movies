package sery.vlasenko.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sery.vlasenko.movies.model.repository.IMovieRepository
import sery.vlasenko.movies.model.repository.MovieRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(
        movieRepository: MovieRepository
    ): IMovieRepository
}