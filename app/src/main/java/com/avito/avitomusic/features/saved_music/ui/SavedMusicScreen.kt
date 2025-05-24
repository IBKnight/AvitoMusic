package com.avito.avitomusic.features.saved_music.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.avito.avitomusic.common.data.model.ApiTrack

@Composable
fun SavedMusicScreen(
    navController: NavController,
    viewModel: SavedMusicViewModel = hiltViewModel<SavedMusicViewModel>(),
) {
    val savedTracks = viewModel.savedTracks.collectAsState().value

    LazyColumn {
        items(savedTracks) { track ->
            Text(text = "${track.title} — ${track.artist}")
        }
    }
}
