package com.avito.avitomusic.features.music_list.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.avitomusic.features.music_list.domain.repository.IApiMusicListRepository
import com.avito.avitomusic.features.music_list.ui.MusicListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApiMusicListViewModel @Inject constructor(
    private val repository: IApiMusicListRepository
) : ViewModel() {

    // Текущее состояние данных
    private val _state = MutableStateFlow<MusicListState>(MusicListState.Loading)
    val state: StateFlow<MusicListState> get() = _state

    // Загрузка данных
    fun loadTracks() {
        viewModelScope.launch {
            _state.value = MusicListState.Loading
            try {
                val tracks = repository.getList()
                _state.value = MusicListState.Loaded(tracks)
            } catch (e: Exception) {
                _state.value = MusicListState.Error("Ошибка загрузки: ${e.message}")
            }
        }
    }

    // Поиск данных
    fun searchTracks(query: String) {
        viewModelScope.launch {
            _state.value = MusicListState.Loading
            try {
                val tracks = repository.search(query)
                _state.value = MusicListState.Loaded(tracks)
            } catch (e: Exception) {
                _state.value = MusicListState.Error("Ошибка поиска: ${e.message}")
            }
        }
    }
}