
package com.app.cineverse.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.app.cineverse.data.local.FavoriteMovieDatabase
import com.app.cineverse.data.remote_db.ApiService
import com.app.cineverse.utils.Constants
import com.app.cineverse.utils.Constants.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFavMovieDatabase(
        @ApplicationContext app:Context
    ) = Room.databaseBuilder(
        app,
        FavoriteMovieDatabase::class.java,
        "movie_db"
    ).build()

    @Singleton
    @Provides
    fun provideFavMovieDao(db: FavoriteMovieDatabase) = db.getFavoriteMovieDao()

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SharedPrefConstants.LOCAL_SHARED_PREF,Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}

