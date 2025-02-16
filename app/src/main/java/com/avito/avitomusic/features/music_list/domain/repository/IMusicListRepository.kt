package com.avito.avitomusic.features.music_list.domain.repository

import com.avito.avitomusic.features.music_list.data.models.TrackModel


interface IApiMusicListRepository {
    suspend fun getList(): List<TrackModel>

    suspend fun search(query: String): List<TrackModel>
}