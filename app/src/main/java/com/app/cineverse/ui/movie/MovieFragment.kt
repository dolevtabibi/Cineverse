package com.app.cineverse.ui.movie

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.cineverse.data.models.Movie
import com.app.cineverse.databinding.FragmentHomeBinding
import com.app.cineverse.utils.Loading
import com.app.cineverse.utils.Success
import com.app.cineverse.utils.Error
import com.app.cineverse.utils.autoCleared
import com.app.cineverse.viewmodel.AuthViewModel
import com.app.cineverse.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(), MovieAdapter.MovieItemListener{

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: MovieViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var genresAdapter: GenresAdapter
    private lateinit var moviesAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val onGenreSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            genresAdapter.getItem(position)?.let {
                viewModel.changeMoviesGenre(it.id.toString())
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MovieAdapter(this)
        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(),3)
        binding.rvMovies.adapter = moviesAdapter

        observerMovieList()
        observeGenreList()

        binding.spinnerGenre.onItemSelectedListener = onGenreSelectedListener
        attachMovieDividerDecoration()
    }
    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionHomeFragmentToDetailsFragment(movie)
        findNavController().navigate(action)
    }

    private fun attachMovieDividerDecoration() {
        val dividerItemDecoration = MovieRvDivider(
            25, 25
        )

        binding.rvMovies.addItemDecoration(dividerItemDecoration)
    }

    private fun observerMovieList() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner) {
            when(it.status) {
                is Loading -> binding.progressBar.isVisible = true
                is Success -> {
                    it.status.data?.let { moviesResponse ->
                        binding.progressBar.isVisible = false
                        moviesAdapter.setMovies(ArrayList(moviesResponse.results))
                    } ?: run {
                        Toast.makeText(requireContext(),getString(com.app.cineverse.R.string.Unknown_error_refreshing), Toast.LENGTH_SHORT).show()
                    }
                }
                is Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeGenreList() {
        viewModel.genresLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> binding.progressBar.isVisible = true
                is Success -> {
                    binding.progressBar.isVisible = false
                    it.status.data?.let { genreList ->
                        genresAdapter = GenresAdapter(
                            requireContext(),
                            genreList.genres
                        )
                        binding.spinnerGenre.adapter = genresAdapter
                    } ?: run {
                        Toast.makeText(
                            requireContext(),
                            getString(com.app.cineverse.R.string.Unknown_error_refreshing),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
