package com.app.cineverse.ui.popular

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.cineverse.data.models.Movie
import com.app.cineverse.databinding.FragmentPopularBinding
import com.app.cineverse.ui.movie.MovieAdapter
import com.app.cineverse.utils.Error
import com.app.cineverse.utils.Loading
import com.app.cineverse.utils.Success
import com.app.cineverse.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PopularFragment : Fragment() {

    private var binding: FragmentPopularBinding by autoCleared()

    private val viewModel : PopularViewModel by viewModels()

    private lateinit var adapter : MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieAdapter(
            object : MovieAdapter.MovieItemListener {
                override fun onMovieClick(movie: Movie) {
                    val action = PopularFragmentDirections.actionPopularFragmentToDetailsFragment(movie)
                    findNavController().navigate(action)
                }
            })
        binding.rvPopular.adapter = adapter
        binding.rvPopular.layoutManager = GridLayoutManager(
            requireContext(),
            3
        )
        viewModel.popularMoviesLive.observe(viewLifecycleOwner) {
            when(it.status) {
                is Loading -> binding.progressBarPopular.isVisible = true
                is Success -> {
                    it.status.data?.let { moviesResponse ->
                        binding.progressBarPopular.isVisible = false
                        adapter.setMovies(ArrayList(moviesResponse.results))
                    } ?: run {
                        Toast.makeText(requireContext(),getString(com.app.cineverse.R.string.Unknown_error_refreshing), Toast.LENGTH_SHORT).show()
                    }
                }
                is Error -> {
                    binding.progressBarPopular.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}