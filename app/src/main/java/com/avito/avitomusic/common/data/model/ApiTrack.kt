package com.avito.avitomusic.common.data.model

import com.avito.avitomusic.features.music_list.domain.models.ArtistModel
import com.google.gson.annotations.SerializedName

abstract class ApiTrack {
    abstract val id: Long
    abstract val title: String
    abstract val duration: Long
    abstract val preview: String
}