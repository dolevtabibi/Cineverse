package com.app.cineverse.ui.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.cineverse.R
import com.app.cineverse.data.local.FavoriteMovie
import com.app.cineverse.databinding.ItemFavoriteBinding
import com.app.cineverse.utils.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var list : List<FavoriteMovie>

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setMovieList(list: List<FavoriteMovie>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteMovie: FavoriteMovie) {
            with(binding) {

                Glide.with(itemView)
                    .load("${Constants.BACK_POSTER_URL}${favoriteMovie.backdropPath}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivMovieBackPoster)
                tvMovieTitle.text = favoriteMovie.original_title

            }
            binding.root.setOnClickListener { onItemClickCallback?.onItemClick(favoriteMovie) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        Log.e("adapter", "Enter bind view holder")
        holder.bind(list[position])
    }

    interface OnItemClickCallback {
        fun onItemClick(favoriteMovie: FavoriteMovie)
    }
}