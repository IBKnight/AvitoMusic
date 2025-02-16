package com.avito.avitomusic.features.music_player.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun PlayerScreen(
    trackID: Long,
    artistID: Long,
    viewModel: PlayerViewModel = hiltViewModel<PlayerViewModel>()
) {
    LaunchedEffect(trackID, artistID) {
        viewModel.loadData(trackID, artistID)
    }

    val tracks by viewModel.tracks.collectAsState()
    val currentTrack by viewModel.currentTrack.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val duration by viewModel.duration.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isSeeking by viewModel.isSeeking.collectAsState()

    // Локальное состояние для слайдера
    var sliderProgress by remember { mutableFloatStateOf(progress) }

    // Синхронизация слайдера с прогрессом
    LaunchedEffect(progress) {
        println(tracks)
        if (!isSeeking) {
            sliderProgress = progress
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = currentTrack?.artist?.picture, // Используем URI для загрузки изображения
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = currentTrack?.title ?: "No track selected",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Индикатор прогресса
        Slider(
            value = sliderProgress,
            onValueChange = { newProgress ->
                sliderProgress = newProgress
                viewModel.seekTo(newProgress)
            },
            onValueChangeFinished = {
                viewModel.seekTo(sliderProgress)
            },
            valueRange = 0f..duration,
            modifier = Modifier.fillMaxWidth()
        )

        // Время
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatTime(sliderProgress.toInt()))
            Text(text = formatTime(duration.toInt()))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.previousTrack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous Track")
            }
            IconButton(onClick = { if (isPlaying) viewModel.pause() else viewModel.play() }) {
                Icon(
                    if (isPlaying) Icons.Default.MoreVert else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )
            }
            IconButton(onClick = { viewModel.nextTrack() }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next Track")
            }
        }
    }
}


fun formatTime(seconds: Int): String {
    TODO("перенести в utils")
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}