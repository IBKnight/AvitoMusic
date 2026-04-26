package com.avito.avitomusic.features.music_player.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.avito.avitomusic.common.data.model.ApiTrack
import com.avito.avitomusic.features.music_player.domain.repository.IPlayerRepository
import com.avito.avitomusic.MusicService.Companion.startService
import com.avito.avitomusic.common.components.MediaPlayerSingleton.mediaPlayer
import com.avito.avitomusic.features.music_player.data.repository.NotificationRepository
import com.avito.avitomusic.features.device_music_list.domain.repository.IDeviceMusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repository: IPlayerRepository,
    private val savedRepository: IDeviceMusicRepository,
    private val musicRepository: NotificationRepository,
    private val application: Application
) : ViewModel() {
    private var progressJob: Job? = null

    val currentTrack: StateFlow<ApiTrack?> = musicRepository.currentTrack
    val isPlaying: StateFlow<Boolean> = musicRepository.isPlaying
    var progress: StateFlow<Float> = musicRepository.progress
    val duration: StateFlow<Float> = musicRepository.duration
    val isSeeking: StateFlow<Boolean> = musicRepository.isSeeking

    private val _tracks = MutableStateFlow<List<ApiTrack>>(emptyList())
    val tracks: StateFlow<List<ApiTrack>> = _tracks.asStateFlow()

    fun loadData(trackID: Long, artistID: Long, context: Context) {
        viewModelScope.launch {
            val track = if (artistID != -1L) {
                val trackListResponse = repository.getTrackList(artistID)
                _tracks.value = trackListResponse.data
                repository.getTrack(trackID)
            } else {
                savedRepository.getTrack(trackID, context).also {
                    _tracks.value = savedRepository.getTracks(context)
                }
            }
            musicRepository.playTrack(track)
        }
        startService(application, "PLAY")
        startProgressUpdates()

    }

    fun play() {
        startService(application, "PLAY")
    }

    fun pause() {
        startService(application, "PAUSE")
    }

    fun seekTo(position: Float) {
        musicRepository.seekTo(position)
    }

    fun nextTrack() {
        startService(application, "SKIPNEXT")
    }

    fun previousTrack() {
        startService(application, "SKIPPREVOUS")
    }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            combine(isPlaying, isSeeking) { playing, seeking ->
                playing && !seeking
            }.collect { shouldUpdate ->
                if (shouldUpdate) {
                    while (isPlaying.value && !isSeeking.value) {
                        musicRepository.setProgress(mediaPlayer.currentPosition.toFloat() / 1000)
                        delay(100)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        progressJob?.cancel()
        progressJob = null
    }

}



