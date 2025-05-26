package com.avito.avitomusic

import com.avito.avitomusic.common.data.ApiMusicDataSource
import com.avito.avitomusic.features.music_player.data.repository.PlayerRepository
import com.avito.avitomusic.features.music_player.domain.repository.IPlayerRepository
import com.avito.avitomusic.features.music_player.data.models.TrackListResponse
import com.avito.avitomusic.features.music_list.data.models.TrackModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class PlayerRepositoryTest {

    private lateinit var repository: IPlayerRepository
    private val dataSource = mock(ApiMusicDataSource::class.java)

    @Before
    fun setUp() {
        repository = PlayerRepository(dataSource)
    }

    @Test
    fun getTrack() = runTest {
        val expected = mock(TrackModel::class.java)
        doReturn(expected).`when`(dataSource).getTrack(1L)

        val result = repository.getTrack(1L)

        assertEquals(expected, result)
    }

    @Test
    fun getTrackList() = runTest {
        val expected = mock(TrackListResponse::class.java)
        doReturn(expected).`when`(dataSource).getTrackList("1", "50")

        val result = repository.getTrackList(1L)

        assertEquals(expected, result)
    }
}
