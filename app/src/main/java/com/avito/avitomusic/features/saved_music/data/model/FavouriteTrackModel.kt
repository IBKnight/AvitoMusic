package com.avito.avitomusic.features.saved_music.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_tracks")
data class FavouriteTrackModel(
    @PrimaryKey val id: Long,
    val title: String,
    val artist: String,
    val artistId: Long,
    val duration: Long,
    val trackImage: String,
    val preview: String
)

