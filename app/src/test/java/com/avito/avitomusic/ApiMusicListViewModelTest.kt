package com.avito.avitomusic

import com.avito.avitomusic.features.music_list.domain.repository.IApiMusicListRepository
import com.avito.avitomusic.features.music_list.ui.MusicListState
import com.avito.avitomusic.features.music_list.ui.viewmodel.ApiMusicListViewModel
import com.avito.avitomusic.features.music_list.data.models.TrackModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class ApiMusicListViewModelTest {

    private lateinit var repository: IApiMusicListRepository
    private lateinit var viewModel: ApiMusicListViewModel

    private val mockTrack = mock(TrackModel::class.java)
    private val trackList = listOf(mockTrack)

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(IApiMusicListRepository::class.java)
        viewModel = ApiMusicListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTracks should emit Loaded state`() = runTest {
        `when`(repository.getList()).thenReturn(trackList)

        viewModel.loadTracks()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(MusicListState.Loaded(trackList), state)
    }

    @Test
    fun `searchTracks with query should emit Loaded state`() = runTest {
        `when`(repository.search("test")).thenReturn(trackList)

        viewModel.searchTracks("test")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(MusicListState.Loaded(trackList), state)
    }

    @Test
    fun `searchTracks with empty query should call getList`() = runTest {
        `when`(repository.getList()).thenReturn(trackList)

        viewModel.searchTracks("")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(MusicListState.Loaded(trackList), state)
    }

    @Test
    fun `loadTracks should emit Error state on exception`() = runTest {
        `when`(repository.getList()).thenThrow(RuntimeException("no connection"))

        viewModel.loadTracks()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assert(state is MusicListState.Error)
    }
}
