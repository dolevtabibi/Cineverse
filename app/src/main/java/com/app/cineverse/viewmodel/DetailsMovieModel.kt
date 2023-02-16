package com.app.cineverse.viewmodel

import androidx.lifecycle.ViewModel
import com.app.cineverse.data.local.FavoriteMovie
import com.app.cineverse.data.local.FavoriteMovieRepository
import com.app.cineverse.data.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsMovieModel @Inject constructor(
    private val repository : FavoriteMovieRepository
) : ViewModel(){
    fun addToFavorite(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteMovie(
                    movie.id,
                    movie.title,
                    movie.overview,
                    movie.posterPath,
                    movie.backdropPath,
                    movie.rating,
                    movie.releaseDate
                )
            )
        }
    }

    suspend fun checkMovie(id: String) = repository.checkMovie(id)

    fun removeFromFavorite(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }
}