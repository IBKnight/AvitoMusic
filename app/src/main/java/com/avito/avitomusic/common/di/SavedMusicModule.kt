package com.avito.avitomusic.common.di

import com.avito.avitomusic.features.saved_music.data.repository.SavedMusicRepoImpl
import com.avito.avitomusic.features.saved_music.domain.repository.ISavedMusicRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SavedMusicModule {
    @Binds
    abstract fun bindSavedMusicRepo(
        impl: SavedMusicRepoImpl
    ): ISavedMusicRepo
}