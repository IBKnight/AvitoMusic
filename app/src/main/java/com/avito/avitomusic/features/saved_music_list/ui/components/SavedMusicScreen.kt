package com.avito.avitomusic.features.saved_music_list.ui.components

import android.content.Context
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.avito.avitomusic.R
import com.avito.avitomusic.common.components.Routes
import com.avito.avitomusic.features.music_list.ui.components.SearchBar
import com.avito.avitomusic.features.saved_music_list.ui.SavedMusicState
import com.avito.avitomusic.features.saved_music_list.ui.viewmodels.SavedMusicViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SavedMusicScreen(
    viewModel: SavedMusicViewModel = hiltViewModel(),
    context: Context,
    navController: NavController
) {

    val permission = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_AUDIO
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    val permissionState = rememberPermissionState(permission)

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    if (permissionState.status.isGranted) {
        val state by viewModel.state

        LaunchedEffect(Unit) {
            viewModel.loadTracks(context)
        }

        when (val currentState = state) {
            is SavedMusicState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SavedMusicState.Loaded -> {
                var searchQuery by remember { mutableStateOf("") }

                Column {
                    SearchBar(
                        searchQuery,
                        { searchQuery = it },
                        {
                            viewModel.searchTracks(searchQuery, context)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(currentState.tracks) { track ->
                            SavedMusicListItem(
                                track = track,
                                onClick = {
                                    navController.navigate("${Routes.PLAYER.route}/${track.id}/${-1}")
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            is SavedMusicState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${currentState.message}", color = Color.Red)
                }
            }
        }
    } else {
        // Если разрешения нет, показываем кнопку для запроса
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(id = R.string.need_permission))
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { permissionState.launchPermissionRequest() }) {
                Text(stringResource(id = R.string.request_permission))
            }
        }
    }
}
