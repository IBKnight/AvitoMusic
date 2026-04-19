package com.avito.avitomusic

import com.avito.avitomusic.common.data.ApiMusicDataSource
import com.avito.avitomusic.features.music_list.data.ApiMusicListRepository
import com.avito.avitomusic.features.music_list.domain.repository.IApiMusicListRepository
import com.avito.avitomusic.features.music_list.data.models.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class ApiMusicListRepositoryTest {

    private lateinit var repository: IApiMusicListRepository
    private val dataSource = mock(ApiMusicDataSource::class.java)

    @Before
    fun setUp() {
        repository = ApiMusicListRepository(dataSource)
    }

    @Test
    fun getList() = runTest {
        val expected = listOf(
            mock(TrackModel::class.java)
        )
        val response = TracksResponse(Tracks(expected))
        doReturn(response).`when`(dataSource).getChart()

        val result = repository.getList()

        assertEquals(expected, result)
    }

    @Test
    fun search() = runTest {
        val expected = listOf(
            mock(TrackModel::class.java)
        )
        val response = Tracks(expected)
        doReturn(response).`when`(dataSource).searchTracks("query")

        val result = repository.search("query")

        assertEquals(expected, result)
    }
}
