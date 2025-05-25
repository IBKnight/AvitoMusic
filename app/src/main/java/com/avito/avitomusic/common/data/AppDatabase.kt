package com.avito.avitomusic.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.avito.avitomusic.features.saved_music.data.FavouriteTrackDao
import com.avito.avitomusic.features.saved_music.data.model.FavouriteTrackModel

@Database(entities = [FavouriteTrackModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedTrackDao(): FavouriteTrackDao
}
