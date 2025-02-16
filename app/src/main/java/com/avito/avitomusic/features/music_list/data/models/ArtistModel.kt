package com.avito.avitomusic.features.music_list.data.models

import com.google.gson.annotations.SerializedName

data class ArtistModel (
    val id: Long,
    val name: String,
    val link: String,
    val picture: String,

    @SerializedName("picture_small")
    val pictureSmall: String,

    @SerializedName("picture_medium")
    val pictureMedium: String,

    @SerializedName("picture_big")
    val pictureBig: String,

    @SerializedName("picture_xl")
    val pictureXl: String,

    val radio: Boolean,
    val tracklist: String,
    val type: String
)
