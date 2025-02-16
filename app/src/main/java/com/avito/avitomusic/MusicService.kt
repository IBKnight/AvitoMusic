package com.avito.avitomusic

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.session.MediaSession
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.avito.avitomusic.features.music_list.data.models.TrackModel
import com.avito.avitomusic.features.music_player.data.models.TrackListItemModel
import com.avito.avitomusic.features.music_player.data.repository.NotificationRepository
import com.avito.avitomusic.features.saved_music_list.data.models.SavedTracksModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MusicService() : Service() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    private lateinit var mediaSession: MediaSession
    private lateinit var notificationManager: NotificationManager

    private val serviceScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initMediaSession()
        observePlaybackState() // Начинаем наблюдать за состоянием воспроизведения
    }

    override fun onDestroy() {
        super.onDestroy()
        // Отменяем все корутины при уничтожении сервиса
        serviceScope.cancel()
        mediaSession.release()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun initMediaSession() {
        mediaSession = MediaSession(this, "MusicService")
        mediaSession.setCallback(object : MediaSession.Callback() {
            override fun onPlay() {
                notificationRepository.play()
            }

            override fun onPause() {
                notificationRepository.pause()
            }

            override fun onStop() {
                stopSelf()
            }
        })
        mediaSession.isActive = true
    }

    private fun observePlaybackState() {
        // Запускаем корутину в serviceScope
        serviceScope.launch {
            // Подписываемся на изменения isPlaying
            notificationRepository.isPlaying.collect { isPlaying ->
                showMediaNotification(isPlaying)
            }
        }

        serviceScope.launch {
            // Подписываемся на изменения currentTrack
            notificationRepository.currentTrack.collect { track ->
                showMediaNotification(notificationRepository.isPlaying.value)
            }
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        when (action) {
            "PLAY" -> {
                notificationRepository.play()
                showMediaNotification(true)
            }

            "PAUSE" -> {
                notificationRepository.pause()
                showMediaNotification(false)
            }

            "STOP" -> {
                notificationRepository.pause()
                stopSelf()
            }

            "SKIPNEXT" -> {
                handleSkipTrack(isNext = true)

            }

            "SKIPPREVOUS" -> {
                handleSkipTrack(isNext = false)

            }
        }

        return START_STICKY
    }

    private fun handleSkipTrack(isNext: Boolean) {
        val currentTrack = notificationRepository.currentTrack.value
        if (currentTrack != null) {
            serviceScope.launch {
                try {

                    val id = when (currentTrack) {
                        is SavedTracksModel? -> currentTrack.id
                        is TrackModel -> currentTrack.artist.id
                        is TrackListItemModel -> currentTrack.artist.id
                        else -> -1L
                    }

                    val trackList =
                        notificationRepository.playerRepository.getTrackList(id).data
                    if (isNext) {
                        notificationRepository.nextTrack(trackList)
                    } else {
                        notificationRepository.previousTrack(trackList)
                    }
                } catch (e: Exception) {
                    // Обработка ошибок (например, логирование или уведомление пользователя)
                    Log.e("MusicService", "Error skipping track", e)
                }
            }
        }
    }

    @SuppressLint("ForegroundServiceType")
    private fun showMediaNotification(isPlaying: Boolean) {
        val notification = buildNotification(isPlaying)
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(isPlaying: Boolean): Notification {
        val playIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, MusicService::class.java).apply { action = "PLAY" },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val pauseIntent = PendingIntent.getService(
            this,
            1,
            Intent(this, MusicService::class.java).apply { action = "PAUSE" },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val stopIntent = PendingIntent.getService(
            this,
            2,
            Intent(this, MusicService::class.java).apply { action = "STOP" },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val skipNextIntent = PendingIntent.getService(
            this,
            3,
            Intent(this, MusicService::class.java).apply { action = "SKIPNEXT" },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val skipPreviousIntent = PendingIntent.getService(
            this,
            4,
            Intent(this, MusicService::class.java).apply { action = "SKIPPREVOUS" },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        // Создаем уведомление
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("${notificationRepository.currentTrack.value?.title}")
            .setContentText("")
            .setLargeIcon(getAlbumArt()) // Обложка альбома
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1)
            )
            .addAction(
                if (isPlaying) R.drawable.pause else R.drawable.play_arrow,
                if (isPlaying) "Pause" else "Play",
                if (isPlaying) pauseIntent else playIntent
            )
            .addAction(androidx.media3.session.R.drawable.media3_icon_stop, "Stop", stopIntent)
            .addAction(R.drawable.skip_next, "Skip Next", skipNextIntent)
            .addAction(R.drawable.skip_previous, "Skip Previous", skipPreviousIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    private fun getAlbumArt(): Bitmap? {
        // Загрузите обложку альбома (например, из сети или ресурсов)
        return BitmapFactory.decodeResource(resources, R.drawable.avito_logo)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "music_channel"
        const val NOTIFICATION_ID = 1

        fun startService(context: Context, action: String) {
            val intent = Intent(context, MusicService::class.java).apply {
                this.action = action
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }
}