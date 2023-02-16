package com.app.cineverse.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.cineverse.utils.Constants

@Entity(tableName = "favorite_movie")
data class FavoriteMovie(
    var id_movie: String,
    val original_title: String,
    val overview : String,
    val poster_path: String,
    val backdropPath: String,
    val rating: Float?,
    val releaseDate: String?
) {
    @PrimaryKey (autoGenerate = true)
    var id : Int = 0
}