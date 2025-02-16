package com.avito.avitomusic.features.music_list.ui

import com.avito.avitomusic.features.music_list.data.models.TrackModel

sealed class MusicListState {
    data object Loading : MusicListState()
    data class Loaded(val tracks: List<TrackModel>) : MusicListState()
    data class Error(val message: String) : MusicListState()
}