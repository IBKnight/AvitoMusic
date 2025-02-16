package com.avito.avitomusic.common.data

import com.avito.avitomusic.features.music_list.domain.models.TrackModel
import com.avito.avitomusic.features.music_list.domain.models.Tracks
import com.avito.avitomusic.features.music_list.domain.models.TracksResponse
import com.avito.avitomusic.features.music_player.domain.models.TrackListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMusicDataSource {
    @GET("chart")
    suspend fun getChart(): TracksResponse

    @GET("search")
    suspend fun searchTracks(
        @Query("q") query: String
    ): Tracks

    @GET("artist/{id}/top")
    suspend fun getTrackList(
        @Path("id") query: String,
        @Query("limit") limit: String
    ): TrackListResponse

    @GET("track/{id}")
    suspend fun getTrack(
        @Path("id") trackID: Long
    ): TrackModel

}