package com.app.cineverse.data.remote_db

import com.app.cineverse.data.models.ListOfGenres
import com.app.cineverse.data.models.Movie
import com.app.cineverse.data.models.MovieResponse
import com.app.cineverse.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT_DISCOVER)
    suspend fun getMoviesByGenre(
        @Query("api_key") apikey: String,
        @Query("with_genres") genre: String
    ): MovieResponse

    @GET(Constants.END_POINT_LIST_OF_GENRES)
    suspend fun getListOfGenres(@Query("api_key") apikey: String): ListOfGenres

    @GET(Constants.END_POINT_DISCOVER)
    suspend fun getMoviesByPopularity(
        @Query("api_key") apikey: String,
        @Query("popularity") popularity: Float): MovieResponse

    /*

    @GET(Constants.END_POINT_DISCOVER)
    suspend fun getMovies(
        @Query("api_key") apikey: String,
    ): Movie

    @GET("search/movie?api_key=$API_KEY")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(
        @Query("page") position: Int
    ): MovieResponse*/
}