package com.avito.avitomusic.features.music_player.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.avito.avitomusic.R
import com.avito.avitomusic.common.data.model.ApiTrack
import com.avito.avitomusic.features.music_list.data.models.TrackModel
import com.avito.avitomusic.features.music_player.data.models.TrackListItemModel
import com.avito.avitomusic.features.music_player.ui.viewmodels.PlayerViewModel
import com.avito.avitomusic.features.saved_music_list.data.models.SavedTracksModel

@SuppressLint("ResourceType")
@Composable
fun PlayerScreen(
    trackID: Long,
    artistID: Long,
    viewModel: PlayerViewModel = hiltViewModel<PlayerViewModel>()
) {
    val context = LocalContext.current

    LaunchedEffect(trackID, artistID) {
        viewModel.loadData(trackID, artistID, context)
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
    ) {

        TrackImage(currentTrack)

        Text(
            text = currentTrack?.title ?: "No track selected",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = when (val track = currentTrack) {
                is SavedTracksModel? -> track?.artist ?: "<unknown>"
                is TrackModel -> track.artist.name
                is TrackListItemModel -> track.artist.name
                else -> "No track selected"
            },
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 18.sp,
            textAlign = TextAlign.Start
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
                Icon(
                    painterResource(id = R.drawable.skip_previous),
                    contentDescription = "Previous Track"
                )
            }
            IconButton(onClick = { if (isPlaying) viewModel.pause() else viewModel.play() }) {
                Icon(
                    if (isPlaying) painterResource(id = R.drawable.pause) else painterResource(id = R.drawable.play_arrow),
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )
            }
            IconButton(onClick = { viewModel.nextTrack() }) {
                Icon(painterResource(id = R.drawable.skip_next), contentDescription = "Next Track")
            }
        }
    }
}

@Composable
fun TrackImage(currentTrack: ApiTrack?) {
    AsyncImage(
        model = when (currentTrack) {
            is TrackModel -> currentTrack.artist.picture
            is TrackListItemModel -> currentTrack.artist.picture
            else -> ContextCompat.getDrawable(LocalContext.current, R.drawable.avito_logo)
        },
        contentDescription = null,
        modifier = Modifier.size(350.dp)
    )

}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
    TODO("перенести в utils")
}