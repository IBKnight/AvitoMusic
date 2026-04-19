package com.avito.avitomusic.features.saved_music.domain.repository

import com.avito.avitomusic.common.data.model.ApiTrack
import com.avito.avitomusic.features.saved_music.data.model.SavedTrackModel
import kotlinx.coroutines.flow.Flow

interface ISavedMusicRepo {
    suspend fun getSavedTracks(): Flow<List<SavedTrackModel>>

    suspend fun saveTrack(track: SavedTrackModel)

    suspend fun removeTrack(track: SavedTrackModel)

    suspend fun isTrackSaved(id: Long): Boolean
}
