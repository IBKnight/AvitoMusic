package com.avito.avitomusic.features.saved_music_list.domain.models

import android.net.Uri

data class SavedTracksModel (
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val path: String,
    val uri: Uri)
