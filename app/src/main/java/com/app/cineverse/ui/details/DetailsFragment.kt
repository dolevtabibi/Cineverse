package com.app.cineverse.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.cineverse.R
import com.app.cineverse.databinding.MovieDetailsItemBinding
import com.app.cineverse.utils.Constants
import com.app.cineverse.utils.autoCleared
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailsFragment : Fragment(){
    private var binding: MovieDetailsItemBinding by autoCleared()
    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailsMovieModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailsItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = MovieDetailsItemBinding.bind(view)

        binding.apply {
            val movie = args.movie
            Glide.with(this@DetailsFragment)
                .load("${Constants.BACK_POSTER_URL}${movie.backdropPath}")
                .into(movieBackdrop)
            if(movie.rating != null)
                movieRating.rating = (movie.rating)!! / 2

            Glide.with(this@DetailsFragment)
                .load("${Constants.POSTER_BASE_URL}${movie.posterPath}")
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
/*
                        progressBar.isVisible = false
*/
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
/*
                        progressBar.isVisible = false
*/
                        movieOverview.isVisible = true
                        movieTitle.isVisible = true
                        return false
                    }

                })
                .into(moviePoster)

            movieOverview.text = movie.overview
            movieTitle.text = movie.title
            movieReleaseDate.text = movie.releaseDate

            var _isChecked = false
            CoroutineScope(Dispatchers.IO).launch{
                val isFavorite = viewModel.checkMovie(movie.id)
                withContext(Main){
                    if (isFavorite) {
                        toggleFavorite.isChecked = true
                        _isChecked = true
                    } else{
                        toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }

            toggleFavorite.setOnClickListener {
                _isChecked = !_isChecked
                if (_isChecked){
                    viewModel.addToFavorite(movie)
                } else{
                    viewModel.removeFromFavorite(movie.id)
                }
                toggleFavorite.isChecked = _isChecked
            }
        }
    }
}


