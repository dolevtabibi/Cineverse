package com.app.cineverse.data.local

import com.app.cineverse.data.local.FavoriteMovie
import com.app.cineverse.data.local.FavoriteMovieDao
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
){
    suspend fun addToFavorite(favoriteMovie: FavoriteMovie) = favoriteMovieDao.addToFavorite(favoriteMovie)

    fun getFavoriteMovies() = favoriteMovieDao.getFavoriteMovie()

    suspend fun checkMovie(id: String) = favoriteMovieDao.checkMovie(id)

    suspend fun removeFromFavorite(id: String) {
        favoriteMovieDao.removeFromFavorite(id)
    }
}