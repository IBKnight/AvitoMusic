package com.avito.avitomusic.features.music_list.data

import com.avito.avitomusic.common.data.ApiMusicDataSource
import com.avito.avitomusic.features.music_list.data.models.TrackModel
import com.avito.avitomusic.features.music_list.domain.repository.IApiMusicListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiMusicListRepository @Inject constructor(
    private val apiMusicDataSource: ApiMusicDataSource
) : IApiMusicListRepository {
    override suspend fun getList(): List<TrackModel> = withContext(Dispatchers.IO) {
        apiMusicDataSource.getChart().tracks.data
    }

    override suspend fun search(query: String): List<TrackModel> = withContext(Dispatchers.IO) {
        apiMusicDataSource.searchTracks(query).data
    }
}