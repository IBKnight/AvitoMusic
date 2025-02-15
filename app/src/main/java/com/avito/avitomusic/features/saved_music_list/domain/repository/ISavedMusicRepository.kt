package com.avito.avitomusic.features.saved_music_list.domain.repository

import android.content.Context
import com.avito.avitomusic.features.saved_music_list.domain.models.SavedTracksModel

interface ISavedMusicRepository {
    fun getTracks(context: Context): List<SavedTracksModel>
    fun searchTracks(query: String, context: Context): List<SavedTracksModel>
}