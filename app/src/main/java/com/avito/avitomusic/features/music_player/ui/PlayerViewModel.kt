package com.avito.avitomusic.features.music_player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.media.MediaPlayer
import android.util.Log
import com.avito.avitomusic.common.data.model.Track
import com.avito.avitomusic.features.music_player.domain.repository.IPlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repository: IPlayerRepository
) : ViewModel() {

    private val mediaPlayer = MediaPlayer()

    // Список треков
    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks

    // Текущий трек
    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack

    // Прогресс воспроизведения
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    // Длительность трека
    private val _duration = MutableStateFlow(0f)
    val duration: StateFlow<Float> = _duration

    // Состояние воспроизведения
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    // Состояние перемотки
    private val _isSeeking = MutableStateFlow(false)
    val isSeeking: StateFlow<Boolean> = _isSeeking

    fun loadData(trackID: Long, artistID: Long) {
        viewModelScope.launch {
            val trackListResponse = repository.getTrackList(artistID)
            _tracks.value = trackListResponse.data

            val track = repository.getTrack(trackID)
            _currentTrack.value = track
            playTrack(track)
        }
    }

    private fun playTrack(track: Track) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(track.preview)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            _duration.value = mediaPlayer.duration.toFloat() / 1000
            play()
        }
        startProgressUpdates()
    }

    fun play() {
        mediaPlayer.start()
        _isPlaying.value = true
    }

    fun pause() {
        mediaPlayer.pause()
        _isPlaying.value = false
    }

    // Перемотка
    fun seekTo(position: Float) {
        _isSeeking.value = true
        mediaPlayer.seekTo((position * 1000).toInt())
        _progress.value = position
        _isSeeking.value = false
    }

    fun nextTrack() {
        Log.i("nextTrack", "${_tracks.value.size}")

        var currentIndex = _tracks.value.indexOfFirst { it.id == _currentTrack.value?.id }
        if (currentIndex != -1 && currentIndex < _tracks.value.size - 1) {
            currentIndex += 1
            _currentTrack.value = _tracks.value[currentIndex]
            playTrack(_tracks.value[currentIndex])
        }
        Log.i("nextTrack", "$currentIndex")
    }

    fun previousTrack() {
        val currentIndex = _tracks.value.indexOfFirst { it.id == _currentTrack.value?.id }
        if (currentIndex > 0) {
            playTrack(_tracks.value[currentIndex - 1])
        }
        Log.i("previousTrack", "$currentIndex")

    }

    private fun startProgressUpdates() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                if (mediaPlayer.isPlaying && !_isSeeking.value) {
                    _progress.value = mediaPlayer.currentPosition.toFloat() / 1000
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}