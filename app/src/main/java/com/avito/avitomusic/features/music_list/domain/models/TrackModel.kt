package com.avito.avitomusic.features.music_list.domain.models

import com.google.gson.annotations.SerializedName

data class TracksResponse(
    @SerializedName("tracks") val tracks: Tracks
)

data class Tracks(
    @SerializedName("data") val data: List<TrackModel>
)

data class TrackModel (
    val id: Long,
    val title: String,

    @SerializedName("title_short")
    val titleShort: String,

    val link: String,
    val duration: Long,
    val rank: Long,

    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,

    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Long,

    @SerializedName("explicit_content_cover")
    val explicitContentCover: Long,

    val preview: String,

    @SerializedName("md5_image")
    val md5Image: String,

    val position: Long,
    val artist: ArtistModel,
    val album: AlbumModel,
    val type: String
)
