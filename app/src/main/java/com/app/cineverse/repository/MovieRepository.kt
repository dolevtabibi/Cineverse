package com.app.cineverse.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.cineverse.data.models.ListOfGenres
import com.app.cineverse.data.models.MovieResponse
import com.app.cineverse.data.remote_db.AppRemoteDataSource
import com.app.cineverse.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: AppRemoteDataSource
) {

    private var _genresLiveData = MutableLiveData<Resource<ListOfGenres>>()
    val genresLiveData : LiveData<Resource<ListOfGenres>> get() = _genresLiveData

    private var _moviesLiveData = MutableLiveData<Resource<MovieResponse>>()
    val moviesLiveData : LiveData<Resource<MovieResponse>> get() = _moviesLiveData

    private var moviesCache : HashMap<String, Resource<MovieResponse>> = HashMap()

    suspend fun getGenres() {
        val resource = remoteDataSource.getListOfGenres()
        _genresLiveData.postValue(resource)
    }

    suspend fun getMoviesByGenre(genreId:String) {
        if(moviesCache.containsKey(genreId))
            _moviesLiveData.postValue(moviesCache[genreId])
        else {
            val resource = remoteDataSource.getMoviesByGenre(genreId)
            moviesCache[genreId] = resource
            _moviesLiveData.postValue(resource)
        }
    }
}