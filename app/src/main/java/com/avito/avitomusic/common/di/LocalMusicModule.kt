package com.avito.avitomusic.common.di

import com.avito.avitomusic.features.music_player.data.repository.NotificationRepository
import com.avito.avitomusic.features.music_player.data.repository.PlayerRepository
import com.avito.avitomusic.features.saved_music_list.data.SavedMusicDatasource
import com.avito.avitomusic.features.saved_music_list.data.SavedMusicRepository
import com.avito.avitomusic.features.saved_music_list.domain.repository.ISavedMusicRepository
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
    fun provideSavedMusicDatasource(): SavedMusicDatasource {
        return SavedMusicDatasource()
    }

    @Provides
    @Singleton
    fun provideSavedMusicRepository(savedMusicDatasource: SavedMusicDatasource): ISavedMusicRepository {
        return SavedMusicRepository(savedMusicDatasource)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(playerRepository: PlayerRepository): NotificationRepository {
        return NotificationRepository(playerRepository)
    }
}