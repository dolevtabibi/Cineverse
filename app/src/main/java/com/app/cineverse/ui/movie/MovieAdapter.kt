
package com.app.cineverse.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.cineverse.databinding.ItemMovieBinding
import com.app.cineverse.data.models.Movie
import com.app.cineverse.utils.Constants.POSTER_BASE_URL
import com.bumptech.glide.Glide

class MovieAdapter(private val listener : MovieItemListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = ArrayList<Movie>()

    class MovieViewHolder(private val movieItemBinding: ItemMovieBinding,
                          private val listener: MovieItemListener
    )
        : RecyclerView.ViewHolder(movieItemBinding.root),
        View.OnClickListener {

        private lateinit var movie: Movie

        init {
            movieItemBinding.root.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            this.movie = movie
            println("Loaded glide.")
            Glide.with(movieItemBinding.root.context)
                .load("${POSTER_BASE_URL}${movie.posterPath}")
                .into(movieItemBinding.ivMoviePoster)
                .clearOnDetach()
            movieItemBinding.tvMovieTitle.text = movie.title

        }
        override fun onClick(v: View?) {
            listener.onMovieClick(movie)
        }
    }

    fun setMovies(movies : Collection<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    interface MovieItemListener{
        fun onMovieClick(movie:Movie)
    }
}