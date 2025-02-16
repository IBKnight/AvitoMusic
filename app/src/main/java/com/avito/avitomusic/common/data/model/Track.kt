package com.avito.avitomusic.common.data.model

import com.avito.avitomusic.features.music_list.domain.models.ArtistModel

abstract class Track {
    abstract val id: Long
    abstract val title: String
    abstract val titleShort: String
    abstract val duration: Long
    abstract val preview: String
    abstract val artist: ArtistModel
    abstract val type: String
    abstract val link: String
    abstract val rank: Long
    abstract val explicitLyrics: Boolean
    abstract val explicitContentLyrics: Long
    abstract val explicitContentCover: Long
    abstract val md5Image: String
}