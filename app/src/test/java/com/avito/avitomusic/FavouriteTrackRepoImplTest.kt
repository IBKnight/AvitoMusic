package com.avito.avitomusic

import com.avito.avitomusic.features.saved_music.data.FavouriteTrackDao
import com.avito.avitomusic.features.saved_music.data.model.FavouriteTrackModel
import com.avito.avitomusic.features.saved_music.domain.repository.IFavouriteMusicRepo
import com.avito.avitomusic.features.saved_music.data.repository.FavouriteTrackRepoImpl
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class FavouriteTrackRepoImplTest {
    private lateinit var repository: IFavouriteMusicRepo
    private val dao = mock(FavouriteTrackDao::class.java)

    private val sampleTrack = FavouriteTrackModel(
        id = 1L,
        title = "Test",
        artist = "Artist",
        artistId = 1L,
        duration = 180L,
        trackImage = "img",
        preview = "prev"
    )

    @Before
    fun setUp() {
        repository = FavouriteTrackRepoImpl(dao)
    }

    @Test
    fun getSavedTracks() = runTest {
        doReturn(flowOf(listOf(sampleTrack))).`when`(dao).getAll()

        val result = repository.getSavedTracks()

        result.collect {
            assertEquals(1, it.size)
            assertEquals("Test", it.first().title)
        }
    }

    @Test
    fun isTrackSaved() = runTest {
        doReturn(true).`when`(dao).isSaved(1L)

        val result = repository.isTrackSaved(1L)

        assertEquals(true, result)
    }
}
