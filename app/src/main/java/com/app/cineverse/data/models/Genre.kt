package com.app.cineverse.data.models

import com.google.gson.annotations.SerializedName
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) {
    override fun toString(): String {
        return name
    }

}