package com.avito.avitomusic.features.saved_music.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avito.avitomusic.features.saved_music.data.model.FavouriteTrackModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteTrackDao {
    @Query("SELECT * FROM saved_tracks")
    fun getAll(): Flow<List<FavouriteTrackModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: FavouriteTrackModel)

    @Delete
    fun delete(track: FavouriteTrackModel)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_tracks WHERE id = :id)")
    fun isSaved(id: Long): Boolean

    @Query(
        """
        SELECT * FROM saved_tracks
        WHERE title   LIKE '%' || :query || '%' COLLATE NOCASE
           OR artist  LIKE '%' || :query || '%' COLLATE NOCASE
    """
    )
    fun search(query: String): Flow<List<FavouriteTrackModel>>
}
