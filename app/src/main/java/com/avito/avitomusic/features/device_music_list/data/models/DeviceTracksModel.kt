package com.avito.avitomusic.features.device_music_list.data.models

import android.net.Uri
import com.avito.avitomusic.common.data.model.ApiTrack

data class DeviceTracksModel(
    override val id: Long,
    override val title: String,
    val artist: String,
    override val duration: Long,
    override val preview: String,
    val uri: Uri,
): ApiTrack()


