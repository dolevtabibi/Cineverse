package com.app.cineverse.viewmodel

import androidx.lifecycle.*
import com.app.cineverse.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
    ) : ViewModel() {

    val genresLiveData = movieRepository.genresLiveData
    val moviesLiveData = movieRepository.moviesLiveData

    init {
        viewModelScope.launch {
            movieRepository.getGenres()
        }
    }

    fun changeMoviesGenre(genreId:String) {
        viewModelScope.launch {
            movieRepository.getMoviesByGenre(genreId)
        }
    }

}