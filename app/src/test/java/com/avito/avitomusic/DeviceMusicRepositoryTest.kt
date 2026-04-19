package com.avito.avitomusic

import com.avito.avitomusic.features.device_music_list.data.DeviceMusicDatasource
import com.avito.avitomusic.features.device_music_list.data.DeviceMusicRepository
import com.avito.avitomusic.features.device_music_list.domain.repository.IDeviceMusicRepository
import com.avito.avitomusic.features.device_music_list.data.models.DeviceTracksModel
import android.content.Context
import junit.framework.TestCase.assertEquals
import org.mockito.Mockito.*
import org.junit.*

class DeviceMusicRepositoryTest {

    private lateinit var repository: IDeviceMusicRepository
    private val dataSource = mock(DeviceMusicDatasource::class.java)
    private val context = mock(Context::class.java)

    private val mockTrack = DeviceTracksModel(
        id = 1L,
        title = "Test Track",
        artist = "Test Artist",
        duration = 180L,
        preview = "http://preview.mp3",
        uri = mock()
    )

    @Before
    fun setUp() {
        repository = DeviceMusicRepository(dataSource)
    }

    @Test
    fun getTracks() {
        doReturn(listOf(mockTrack)).`when`(dataSource).getTracks(context)

        val result = repository.getTracks(context)

        assertEquals(1, result.size)
        assertEquals(mockTrack, result.first())
    }

    @Test
    fun searchTracks() {
        val tracks = listOf(
            mockTrack,
            mockTrack.copy(id = 2L, title = "Another", artist = "Someone Else")
        )
        doReturn(tracks).`when`(dataSource).getTracks(context)

        val result = repository.searchTracks("Test", context)

        assertEquals(1, result.size)
        assertEquals("Test Track", result.first().title)
    }

    @Test
    fun getTrack() {
        val tracks = listOf(
            mockTrack,
            mockTrack.copy(id = 2L)
        )
        doReturn(tracks).`when`(dataSource).getTracks(context)

        val result = repository.getTrack(2L, context)

        assertEquals(2L, result.id)
    }
}
