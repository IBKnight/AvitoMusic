package com.avito.avitomusic.common.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.avito.avitomusic.features.saved_music.data.SavedTrackDao
import com.avito.avitomusic.features.saved_music.data.model.SavedTrackModel

@Database(entities = [SavedTrackModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedTrackDao(): SavedTrackDao
}
