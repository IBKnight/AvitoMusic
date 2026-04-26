package com.avito.avitomusic.common.di

import com.avito.avitomusic.features.saved_music.data.FavouriteTrackDao
import com.avito.avitomusic.features.saved_music.data.repository.FavouriteTrackRepoImpl
import com.avito.avitomusic.features.saved_music.domain.repository.IFavouriteMusicRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouriteMusicModule {
    @Binds
    @Singleton
    abstract fun bindSavedMusicRepo(favouriteTrackRepoImpl: FavouriteTrackRepoImpl): IFavouriteMusicRepo
}