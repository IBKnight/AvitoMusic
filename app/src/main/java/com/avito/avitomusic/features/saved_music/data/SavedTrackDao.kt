package com.avito.avitomusic.features.saved_music.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avito.avitomusic.features.saved_music.data.model.SavedTrackModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedTrackDao {
    @Query("SELECT * FROM saved_tracks")
    fun getAll(): Flow<List<SavedTrackModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: SavedTrackModel)

    @Delete
    fun delete(track: SavedTrackModel)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_tracks WHERE id = :id)")
    fun isSaved(id: Long): Boolean
}
