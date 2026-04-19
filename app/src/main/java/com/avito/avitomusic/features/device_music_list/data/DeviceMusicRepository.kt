package com.avito.avitomusic.features.device_music_list.data

import android.content.Context
import com.avito.avitomusic.features.device_music_list.data.models.DeviceTracksModel
import com.avito.avitomusic.features.device_music_list.domain.repository.IDeviceMusicRepository

class DeviceMusicRepository(private val dataSource: DeviceMusicDatasource) : IDeviceMusicRepository {
    override fun getTracks(context: Context): List<DeviceTracksModel> {
        return dataSource.getTracks(context)
    }

    override fun searchTracks(query: String, context: Context): List<DeviceTracksModel> {
        val allTracks = dataSource.getTracks(context)
        return allTracks.filter { track ->
            track.title.contains(query, ignoreCase = true) || track.artist.contains(
                query,
                ignoreCase = true
            )
        }
    }

    override fun getTrack(id: Long, context: Context): DeviceTracksModel {
        val trackList = dataSource.getTracks(context)
        return trackList[trackList.indexOfFirst { savedTrack -> savedTrack.id == id }]
    }

}