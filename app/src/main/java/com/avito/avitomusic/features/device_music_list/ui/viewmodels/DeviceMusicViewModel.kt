package com.avito.avitomusic.features.device_music_list.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.avitomusic.features.device_music_list.domain.repository.IDeviceMusicRepository
import com.avito.avitomusic.features.device_music_list.ui.DeviceMusicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceMusicViewModel @Inject constructor(
    private val repository: IDeviceMusicRepository
) : ViewModel() {
    private val _state = mutableStateOf<DeviceMusicState>(DeviceMusicState.Loading)
    val state: State<DeviceMusicState> get() = _state

    fun loadTracks(context: Context) {
        _state.value = DeviceMusicState.Loading
        viewModelScope.launch {
            try {
                val tracks = repository.getTracks(context)
                Log.i("loadSavedTracks", "$tracks")
                _state.value = DeviceMusicState.Loaded(tracks)
            } catch (e: Exception) {
                _state.value = DeviceMusicState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun searchTracks(query: String, context: Context) {
        val currentState = _state.value
        if (currentState is DeviceMusicState.Loaded) {
            val filteredTracks = repository.searchTracks(query, context)
            _state.value = DeviceMusicState.Loaded(filteredTracks)
        }
    }
}