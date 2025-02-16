package com.avito.avitomusic.features.music_list.data.models

import com.avito.avitomusic.common.data.model.ApiTrack
import com.google.gson.annotations.SerializedName

data class TracksResponse(
    @SerializedName("tracks") val tracks: Tracks
)

data class Tracks(
    @SerializedName("data") val data: List<TrackModel>
)

data class TrackModel(
    override val id: Long,
    override val title: String,

    @SerializedName("title_short")
    val titleShort: String,

    val link: String,
    override val duration: Long,
    val rank: Long,

    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,

    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Long,

    @SerializedName("explicit_content_cover")
    val explicitContentCover: Long,

    override val preview: String,

    @SerializedName("md5_image")
    val md5Image: String,

    val position: Long,
    val artist: ArtistModel,
    val album: AlbumModel,
    val type: String
) : ApiTrack()