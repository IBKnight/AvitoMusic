package com.avito.avitomusic.features.music_list.domain.models

import com.avito.avitomusic.common.data.model.Track
import com.google.gson.annotations.SerializedName

data class TracksResponse(
    @SerializedName("tracks") val tracks: Tracks
)

data class Tracks(
    @SerializedName("data") val data: List<TrackModel>
)

data class TrackModel (
    override val id: Long,
    override val title: String,

    @SerializedName("title_short")
    override val titleShort: String,

    override val link: String,
    override val duration: Long,
    override val rank: Long,

    @SerializedName("explicit_lyrics")
    override val explicitLyrics: Boolean,

    @SerializedName("explicit_content_lyrics")
    override val explicitContentLyrics: Long,

    @SerializedName("explicit_content_cover")
    override val explicitContentCover: Long,

    override val preview: String,

    @SerializedName("md5_image")
    override val md5Image: String,

    val position: Long,
    override val artist: ArtistModel,
    val album: AlbumModel,
    override val type: String
): Track()
