package com.avito.avitomusic.features.music_list.data

import com.avito.avitomusic.features.music_list.domain.models.TrackModel
import com.avito.avitomusic.features.music_list.domain.repository.IApiMusicListRepository
import javax.inject.Inject

class ApiMusicListRepository @Inject constructor(
    private val apiMusicDataSource: ApiMusicDataSource
) : IApiMusicListRepository {
    override suspend fun getList(): List<TrackModel> {
        return apiMusicDataSource.getChart().tracks.data
    }

    override suspend fun search(query: String): List<TrackModel> {
        return apiMusicDataSource.searchTracks(query).data
    }
}