package com.avito.avitomusic.common.data.model

abstract class ApiTrack {
    abstract val id: Long
    abstract val title: String
    abstract val duration: Long
    abstract val preview: String
}