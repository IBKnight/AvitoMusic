package com.avito.avitomusic.common.di

import com.avito.avitomusic.features.music_player.data.repository.NotificationRepository
import com.avito.avitomusic.features.music_player.data.repository.PlayerRepository
import com.avito.avitomusic.features.device_music_list.data.DeviceMusicDatasource
import com.avito.avitomusic.features.device_music_list.data.DeviceMusicRepository
import com.avito.avitomusic.features.device_music_list.domain.repository.IDeviceMusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalMusicModule {

    @Provides
    @Singleton
    fun provideDeviceMusicDatasource(): DeviceMusicDatasource {
        return DeviceMusicDatasource()
    }

    @Provides
    @Singleton
    fun provideDeviceMusicRepository(deviceMusicDatasource: DeviceMusicDatasource): IDeviceMusicRepository {
        return DeviceMusicRepository(deviceMusicDatasource)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(playerRepository: PlayerRepository): NotificationRepository {
        return NotificationRepository(playerRepository)
    }
}