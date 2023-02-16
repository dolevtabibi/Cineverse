package com.app.cineverse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.cineverse.data.local.FavoriteMovie
import com.app.cineverse.data.local.FavoriteMovieDao


@Database(
    entities = [FavoriteMovie::class],
    version = 1
)
abstract class FavoriteMovieDatabase : RoomDatabase() {
    abstract fun getFavoriteMovieDao(): FavoriteMovieDao
}