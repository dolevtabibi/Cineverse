package com.app.cineverse.data.remote_db

import com.app.cineverse.utils.Constants.API_KEY
import javax.inject.Inject

class AppRemoteDataSource @Inject constructor(
    private val apiService: ApiService
)  : BaseDataSource() {

    suspend fun getListOfGenres() = getResult {
        apiService.getListOfGenres(API_KEY)
    }
    suspend fun getMoviesByGenre(genreId:String) = getResult {
        apiService.getMoviesByGenre(API_KEY,genreId)
    }

    suspend fun getMoviesByPopularity(popularity:Float) = getResult {
        apiService.getMoviesByPopularity(API_KEY, popularity)
    }
}