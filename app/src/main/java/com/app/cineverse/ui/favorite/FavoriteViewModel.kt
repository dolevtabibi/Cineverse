package com.app.cineverse.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.cineverse.data.local.FavoriteMovie
import com.app.cineverse.data.local.FavoriteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteMovieRepository
) : ViewModel(){
    val movies = repository.getFavoriteMovies()

    fun addFavorite(favorite:FavoriteMovie) =
        viewModelScope.launch {
            repository.addToFavorite(favorite)
        }
    fun removeFavorite(favorite: FavoriteMovie)
            = viewModelScope.launch {
        repository.addToFavorite(favorite)
    }

    fun toggleFavorite(favorite:FavoriteMovie) =
        viewModelScope.launch {
            if(isFavorite(favorite.id_movie))
                removeFavorite(favorite)
            else
                addFavorite(favorite)
        }
    suspend fun isFavorite(favorite:String) =
        repository.checkMovie(favorite) > 0
}