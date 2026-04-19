package com.avito.avitomusic.features.saved_music.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.avitomusic.common.data.model.ApiTrack
import com.avito.avitomusic.features.saved_music.data.model.SavedTrackModel
import com.avito.avitomusic.features.saved_music.domain.repository.ISavedMusicRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMusicViewModel @Inject constructor(
    private val repo: ISavedMusicRepo
) : ViewModel() {
    private val _savedTracks = MutableStateFlow<List<SavedTrackModel>>(emptyList())
    val savedTracks: StateFlow<List<SavedTrackModel>> get() = _savedTracks

    init {
        viewModelScope.launch {
            repo.getSavedTracks().collect { apiTracks ->
                _savedTracks.value = apiTracks.map { track ->
                    track
                }
            }
        }
    }

    fun isTrackSavedState(trackId: Long): StateFlow<Boolean> =
        _savedTracks
            .map { list -> list.any { it.id == trackId } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = false
            )


    fun toggleSaved(track: SavedTrackModel) = viewModelScope.launch {
        if (repo.isTrackSaved(track.id)) {
            repo.removeTrack(track)
        } else {
            repo.saveTrack(track)
        }
    }
}
