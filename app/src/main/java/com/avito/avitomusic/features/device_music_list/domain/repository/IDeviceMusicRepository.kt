package com.avito.avitomusic.features.device_music_list.domain.repository

import android.content.Context
import com.avito.avitomusic.features.device_music_list.data.models.DeviceTracksModel

interface IDeviceMusicRepository {
    fun getTracks(context: Context): List<DeviceTracksModel>
    fun searchTracks(query: String, context: Context): List<DeviceTracksModel>
    fun getTrack(id: Long, context: Context): DeviceTracksModel
}