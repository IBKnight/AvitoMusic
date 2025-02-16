package com.avito.avitomusic.features.music_player.domain.models

import com.avito.avitomusic.common.data.model.Track
import com.avito.avitomusic.features.music_list.domain.models.ArtistModel
import com.google.gson.annotations.SerializedName

data class TrackListItemModel(
    override val id: Long,
    val readable: Boolean,
    override val title: String,

    @SerializedName("title_short")
    override val titleShort: String,

    val isrc: String,
    override val link: String,
    override val duration: Long,

    @SerializedName("track_position")
    val trackPosition: Long,

    @SerializedName("disk_number")
    val diskNumber: Long,

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

    override val artist: ArtistModel,
    override val type: String,

    @SerializedName("title_version")
    val titleVersion: String? = null
) : Track()


