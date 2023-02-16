package com.app.cineverse.utils

object Constants {

    const val API_KEY = "c03cd1b9c67aedec8b2b75a0f230c3a3"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
    const val BACK_POSTER_URL = "https://image.tmdb.org/t/p/w1280"
    const val END_POINT_DISCOVER = "discover/movie"
    const val END_POINT_LIST_OF_GENRES = "genre/movie/list"

    object SharedPrefConstants {
        const val LOCAL_SHARED_PREF = "local_shared_pref"
        const val USER_SESSION = "user_session"
    }

    object FireStoreCollection{
        const val USER = "users"
    }


}

