package sery.vlasenko.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import sery.vlasenko.movies.BuildConfig
import sery.vlasenko.movies.model.MovieService
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @ApiKey
    fun provideApiKey(): String = BuildConfig.MOVIE_API_KEY

    @Provides
    @ApiUrl
    fun provideBaseUrl(): String = BuildConfig.MOVIE_API

    @Provides
    @Singleton
    fun provideOkhttpClient(authInterceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideAuthInterceptor(@ApiKey apiKey: String): Interceptor = Interceptor { chain ->
        val request = chain.request()

        val authUrl = request
            .url()
            .newBuilder()
            .addQueryParameter("api-key", apiKey)
            .build()

        val authRequest = request.newBuilder().url(authUrl).build()
        chain.proceed(authRequest)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @ApiUrl baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)
}

@Qualifier
@Retention
annotation class ApiUrl

@Qualifier
@Retention
annotation class ApiKey