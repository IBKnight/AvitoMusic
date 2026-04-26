package com.avito.avitomusic.features.music_player.data.repository

import com.avito.avitomusic.common.data.ApiMusicDataSource
import com.avito.avitomusic.common.di.IoDispatcher
import com.avito.avitomusic.features.music_list.data.models.TrackModel
import com.avito.avitomusic.features.music_player.data.models.TrackListResponse
import com.avito.avitomusic.features.music_player.domain.repository.IPlayerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayerRepository @Inject constructor(
    private val apiMusicDataSource: ApiMusicDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) :
    IPlayerRepository {
    override suspend fun getTrackList(trackID: Long): TrackListResponse = withContext(ioDispatcher) {
        apiMusicDataSource.getTrackList(trackID.toString(), LIMIT.toString())
    }

    override suspend fun getTrack(trackID: Long): TrackModel = withContext(ioDispatcher) {
        apiMusicDataSource.getTrack(trackID)
    }

    private companion object {
        const val LIMIT: Int = 50
    }
}