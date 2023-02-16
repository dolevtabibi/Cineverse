package com.app.cineverse.data.models


import com.google.gson.annotations.SerializedName

data class ListOfGenres(
    @SerializedName("genres")
    val genres: List<Genre>
)