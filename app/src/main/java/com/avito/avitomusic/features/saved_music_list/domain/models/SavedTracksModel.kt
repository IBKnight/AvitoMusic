package com.avito.avitomusic.features.saved_music_list.domain.models

import android.net.Uri
import com.avito.avitomusic.common.data.model.ApiTrack

data class SavedTracksModel(
    override val id: Long,
    override val title: String,
    val artist: String,
    override val duration: Long,
    //Путь до трека
    override val preview: String,
    val uri: Uri,
): ApiTrack()
