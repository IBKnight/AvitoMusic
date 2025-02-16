package com.avito.avitomusic.features.music_player.data.repository

import com.avito.avitomusic.common.data.ApiMusicDataSource
import com.avito.avitomusic.features.music_list.domain.models.TrackModel
import com.avito.avitomusic.features.music_player.domain.models.TrackListResponse
import com.avito.avitomusic.features.music_player.domain.repository.IPlayerRepository
import javax.inject.Inject


class PlayerRepository @Inject constructor(private val apiMusicDataSource: ApiMusicDataSource) :
    IPlayerRepository {
    override suspend fun getTrackList(trackID: Long): TrackListResponse {
        return apiMusicDataSource.getTrackList(trackID.toString(), LIMIT.toString())
    }

    override suspend fun getTrack(trackID: Long): TrackModel {
        return apiMusicDataSource.getTrack(trackID)
    }

    companion object {
        const val LIMIT: Int = 50
    }
}