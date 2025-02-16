package com.avito.avitomusic.features.music_player.data.repository

import com.avito.avitomusic.common.components.MediaPlayerSingleton.mediaPlayer
import com.avito.avitomusic.common.data.model.ApiTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NotificationRepository @Inject constructor(val playerRepository: PlayerRepository) {

    // Текущий трек
    private val _currentTrack = MutableStateFlow<ApiTrack?>(null)
    val currentTrack: StateFlow<ApiTrack?> = _currentTrack

    // Состояние воспроизведения
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    // Прогресс воспроизведения
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    // Длительность трека
    private val _duration = MutableStateFlow(0f)
    val duration: StateFlow<Float> = _duration

    fun playTrack(track: ApiTrack) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(track.preview)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            _duration.value = mediaPlayer.duration.toFloat() / 1000
            play()
        }
        _currentTrack.value = track
    }

    fun play() {
        mediaPlayer.start()
        _isPlaying.value = true
    }

    fun pause() {
        mediaPlayer.pause()
        _isPlaying.value = false
    }

    fun seekTo(position: Float) {
        mediaPlayer.seekTo((position * 1000).toInt())
        _progress.value = position
    }

    fun nextTrack(tracks: List<ApiTrack>) {
        val currentIndex = tracks.indexOfFirst { it.id == _currentTrack.value?.id }
        if (currentIndex != -1 && currentIndex < tracks.size - 1) {
            _currentTrack.value = tracks[currentIndex + 1]
            playTrack(tracks[currentIndex + 1])
        }
    }

    fun previousTrack(tracks: List<ApiTrack>) {
        val currentIndex = tracks.indexOfFirst { it.id == _currentTrack.value?.id }
        if (currentIndex > 0) {
            _currentTrack.value = tracks[currentIndex - 1]
            playTrack(tracks[currentIndex - 1])
        }
    }

//    fun startProgressUpdates() {
//        // Запускаем обновление прогресса в корутине
//        coroutineScope(Dispatchers.Main)launch {
//            while (true) {
//                delay(1000) // Обновляем каждую секунду
//                if (mediaPlayer.isPlaying) {
//                    _progress.value = mediaPlayer.currentPosition.toFloat() / 1000
//                }
//            }
//        }
//    }
}