package com.avito.avitomusic.features.device_music_list.ui

import com.avito.avitomusic.features.device_music_list.data.models.DeviceTracksModel

sealed class DeviceMusicState {
    data object Loading : DeviceMusicState()
    data class Loaded(val tracks: List<DeviceTracksModel>) : DeviceMusicState()
    data class Error(val message: String) : DeviceMusicState()
}