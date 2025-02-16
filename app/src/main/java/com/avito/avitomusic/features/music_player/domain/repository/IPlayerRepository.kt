package com.avito.avitomusic.features.music_player.domain.repository

import com.avito.avitomusic.features.music_list.domain.models.TrackModel
import com.avito.avitomusic.features.music_player.domain.models.TrackListResponse

interface IPlayerRepository {
    suspend fun getTrackList(trackID: Long): TrackListResponse
    suspend fun getTrack(trackID: Long): TrackModel
}