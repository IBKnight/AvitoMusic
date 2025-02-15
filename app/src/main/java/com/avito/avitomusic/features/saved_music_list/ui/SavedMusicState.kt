package com.avito.avitomusic.features.saved_music_list.ui

import com.avito.avitomusic.features.saved_music_list.domain.models.SavedTracksModel

sealed class SavedMusicState {
    data object Loading : SavedMusicState()
    data class Loaded(val tracks: List<SavedTracksModel>) : SavedMusicState()
    data class Error(val message: String) : SavedMusicState()
}