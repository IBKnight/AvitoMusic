package com.avito.avitomusic.features.saved_music.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.avito.avitomusic.common.components.Routes
import com.avito.avitomusic.features.music_list.ui.components.SearchBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavouriteMusicScreen(
    navController: NavController,
    viewModel: FavouriteMusicViewModel = hiltViewModel<FavouriteMusicViewModel>(),
) {
    val savedTracks = viewModel.savedTracks.collectAsState().value
    var query by remember { mutableStateOf("") }

    Scaffold {
        Column {
            SearchBar(query, { newValue ->
                query = newValue
                viewModel.searchTrack(query)

            }, {
                query = ""
                viewModel.searchTrack(query)
            })
            LazyColumn {
                items(savedTracks) { track ->
                    FavouriteListItem(
                        track = track,
                        onClick =
                        { navController.navigate("${Routes.PLAYER.route}/${track.id}/${track.artistId}") },
                    )
//            Text(text = "${track.title} — ${track.artist}")
                }
            }
        }
    }


}
