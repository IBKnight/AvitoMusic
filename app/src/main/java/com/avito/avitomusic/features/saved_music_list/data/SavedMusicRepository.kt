package com.avito.avitomusic.features.saved_music_list.data

import android.content.Context
import com.avito.avitomusic.features.saved_music_list.data.models.SavedTracksModel
import com.avito.avitomusic.features.saved_music_list.domain.repository.ISavedMusicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SavedMusicRepository(private val dataSource: SavedMusicDatasource) : ISavedMusicRepository {
    override suspend fun getTracks(context: Context): List<SavedTracksModel> = withContext(Dispatchers.IO) {
        dataSource.getTracks(context)
    }

    override suspend fun searchTracks(query: String, context: Context): List<SavedTracksModel> = withContext(Dispatchers.IO) {
        val allTracks = dataSource.getTracks(context)
        allTracks.filter { track ->
            track.title.contains(query, ignoreCase = true) || track.artist.contains(
                query,
                ignoreCase = true
            )
        }
    }

    override suspend fun getTrack(id: Long, context: Context): SavedTracksModel = withContext(Dispatchers.IO) {
        val trackList = dataSource.getTracks(context)
        trackList[trackList.indexOfFirst { savedTrack -> savedTrack.id == id }]
    }

}