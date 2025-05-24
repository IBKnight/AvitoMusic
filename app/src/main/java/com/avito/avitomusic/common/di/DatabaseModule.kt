package com.avito.avitomusic.common.di

import android.content.Context
import androidx.room.Room
import com.avito.avitomusic.common.data.AppDatabase
import com.avito.avitomusic.features.saved_music.data.SavedTrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "music_db").build()

    @Provides
    fun provideSavedTrackDao(db: AppDatabase): SavedTrackDao = db.savedTrackDao()
}