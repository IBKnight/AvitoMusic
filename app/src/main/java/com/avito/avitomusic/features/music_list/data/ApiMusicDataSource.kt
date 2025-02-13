package com.avito.avitomusic.features.music_list.data

import com.avito.avitomusic.features.music_list.domain.models.TrackModel
import com.avito.avitomusic.features.music_list.domain.models.Tracks
import com.avito.avitomusic.features.music_list.domain.models.TracksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMusicDataSource {
    @GET("chart/")
    suspend fun getChart(): TracksResponse

    @GET("search")
    suspend fun searchTracks(
        @Query("q") query: String
    ): Tracks

}