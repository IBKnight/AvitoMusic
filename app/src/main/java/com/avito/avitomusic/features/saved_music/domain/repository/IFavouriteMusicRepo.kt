package com.avito.avitomusic.features.saved_music.domain.repository

import com.avito.avitomusic.features.saved_music.data.model.FavouriteTrackModel
import kotlinx.coroutines.flow.Flow

interface IFavouriteMusicRepo {
    suspend fun getSavedTracks(): Flow<List<FavouriteTrackModel>>

    suspend fun saveTrack(track: FavouriteTrackModel)

    suspend fun removeTrack(track: FavouriteTrackModel)

    suspend fun isTrackSaved(id: Long): Boolean

    suspend fun search(query: String): Flow<List<FavouriteTrackModel>>
}
