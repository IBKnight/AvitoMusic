package com.avito.avitomusic.features.saved_music.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.avitomusic.features.saved_music.data.model.FavouriteTrackModel
import com.avito.avitomusic.features.saved_music.domain.repository.IFavouriteMusicRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMusicViewModel @Inject constructor(
    private val repo: IFavouriteMusicRepo
) : ViewModel() {
    private val _savedTracks = MutableStateFlow<List<FavouriteTrackModel>>(emptyList())
    val savedTracks: StateFlow<List<FavouriteTrackModel>> get() = _savedTracks

    init {
        viewModelScope.launch { getTracks() }
    }

    private suspend fun getTracks() =
        repo.getSavedTracks().collect { apiTracks ->
            _savedTracks.value = apiTracks.map { track ->
                track
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


    fun toggleSaved(track: FavouriteTrackModel) = viewModelScope.launch {
        if (repo.isTrackSaved(track.id)) {
            repo.removeTrack(track)
        } else {
            repo.saveTrack(track)
        }
    }

    @OptIn(FlowPreview::class)
    fun searchTrack(query: String) = viewModelScope.launch {
        if (query.isEmpty()) {
            val all = repo.getSavedTracks().debounce(300).first()
            _savedTracks.value = all
        } else {
            // иначе — обычный дебаунс + поиск

            Log.i("queryDB", query)
            repo.search(query)
                .debounce(300)
                .collect { dbTracks ->
                    _savedTracks.value = dbTracks
                }
        }
    }
}
