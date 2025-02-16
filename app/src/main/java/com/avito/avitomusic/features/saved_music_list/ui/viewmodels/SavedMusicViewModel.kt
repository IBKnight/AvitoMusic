package com.avito.avitomusic.features.saved_music_list.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.avitomusic.features.music_list.ui.MusicListState
import com.avito.avitomusic.features.saved_music_list.domain.repository.ISavedMusicRepository
import com.avito.avitomusic.features.saved_music_list.ui.SavedMusicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMusicViewModel @Inject constructor(
    private val repository: ISavedMusicRepository
) : ViewModel() {
    private val _state = mutableStateOf<SavedMusicState>(SavedMusicState.Loading)
    val state: State<SavedMusicState> get() = _state

    fun loadTracks(context: Context) {
        _state.value = SavedMusicState.Loading
        viewModelScope.launch {
            try {
                val tracks = repository.getTracks(context)
                Log.i("loadSavedTracks", "$tracks")
                _state.value = SavedMusicState.Loaded(tracks)
            } catch (e: Exception) {
                _state.value = SavedMusicState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun searchTracks(query: String, context: Context) {
        val currentState = _state.value
        if (currentState is SavedMusicState.Loaded) {
            val filteredTracks = repository.searchTracks(query, context)
            _state.value = SavedMusicState.Loaded(filteredTracks)
        }
    }
}