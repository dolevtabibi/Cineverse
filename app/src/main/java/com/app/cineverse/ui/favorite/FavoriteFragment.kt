package com.app.cineverse.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.cineverse.data.local.FavoriteMovie
import com.app.cineverse.databinding.FragmentFavoriteBinding
import com.app.cineverse.data.models.Movie
import com.app.cineverse.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(){

    private var binding: FragmentFavoriteBinding by autoCleared()
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteAdapter()

        viewModel.movies.observe(viewLifecycleOwner){
            adapter.setMovieList(it)
            binding.apply {
                rvFavorites.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
                rvFavorites.adapter = adapter
            }
        }

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClick(favoriteMovie: FavoriteMovie) {
                val movie = Movie(
                    favoriteMovie.id_movie,
                    favoriteMovie.original_title,
                    favoriteMovie.overview,
                    favoriteMovie.poster_path,
                    favoriteMovie.backdropPath,
                    favoriteMovie.rating,
                    favoriteMovie.releaseDate
                )
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(movie)
                findNavController().navigate(action)
            }
        })
    }
}