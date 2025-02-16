package com.avito.avitomusic.features.music_player.domain.models

import com.avito.avitomusic.common.data.model.ApiTrack
import com.avito.avitomusic.features.music_list.domain.models.ArtistModel
import com.google.gson.annotations.SerializedName

data class TrackListItemModel(
    override val id: Long,
    val readable: Boolean,
    override val title: String,

    @SerializedName("title_short")
    val titleShort: String,

    val isrc: String,
    val link: String,
    override val duration: Long,

    @SerializedName("track_position")
    val trackPosition: Long,

    @SerializedName("disk_number")
    val diskNumber: Long,

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

    val artist: ArtistModel,
    val type: String,

    @SerializedName("title_version")
    val titleVersion: String? = null
) : ApiTrack()