package com.app.cineverse.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.cineverse.data.models.MovieResponse
import com.app.cineverse.data.remote_db.AppRemoteDataSource
import com.app.cineverse.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    dataSource: AppRemoteDataSource
): ViewModel()  {

    private var _popularMoviesLive : MutableLiveData<Resource<MovieResponse>> = MutableLiveData()

    val popularMoviesLive : LiveData<Resource<MovieResponse>> get() = _popularMoviesLive

    init {
        viewModelScope.launch {
            _popularMoviesLive.postValue(
                dataSource.getMoviesByPopularity(5f)
            )
        }
    }
}