package com.avito.avitomusic.features.saved_music_list.data

import android.content.Context
import com.avito.avitomusic.features.saved_music_list.data.models.SavedTracksModel
import com.avito.avitomusic.features.saved_music_list.domain.repository.ISavedMusicRepository

class SavedMusicRepository(private val dataSource: SavedMusicDatasource) : ISavedMusicRepository {
    override fun getTracks(context: Context): List<SavedTracksModel> {
        return dataSource.getTracks(context)
    }

    override fun searchTracks(query: String, context: Context): List<SavedTracksModel> {
        val allTracks = dataSource.getTracks(context)
        return allTracks.filter { track ->
            track.title.contains(query, ignoreCase = true) || track.artist.contains(
                query,
                ignoreCase = true
            )
        }
    }

    override fun getTrack(id: Long, context: Context): SavedTracksModel {
        val trackList = dataSource.getTracks(context)
        return trackList[trackList.indexOfFirst { savedTrack -> savedTrack.id == id }]
    }

}