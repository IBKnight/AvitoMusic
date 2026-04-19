package com.avito.avitomusic.features.music_player.data.repository

import com.avito.avitomusic.common.data.ApiMusicDataSource
import com.avito.avitomusic.features.music_list.data.models.TrackModel
import com.avito.avitomusic.features.music_player.data.models.TrackListResponse
import com.avito.avitomusic.features.music_player.domain.repository.IPlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//TODO(IBKnight): надо унести в DI object с диспатчерами
class PlayerRepository @Inject constructor(private val apiMusicDataSource: ApiMusicDataSource) :
    IPlayerRepository {
    override suspend fun getTrackList(trackID: Long): TrackListResponse = withContext(dispatcher) {
        apiMusicDataSource.getTrackList(trackID.toString(), LIMIT.toString())
    }

    override suspend fun getTrack(trackID: Long): TrackModel = withContext(dispatcher) {
        apiMusicDataSource.getTrack(trackID)
    }

    companion object {
        const val LIMIT: Int = 50
        val dispatcher = Dispatchers.IO
    }
}