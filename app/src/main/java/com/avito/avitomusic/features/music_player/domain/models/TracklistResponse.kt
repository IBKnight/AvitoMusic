package com.avito.avitomusic.features.music_player.domain.models

data class TrackListResponse (
    val data: List<TrackListItemModel>,
    val total: Long
)