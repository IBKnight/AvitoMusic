package com.avito.avitomusic.features.saved_music_list.data

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.avito.avitomusic.features.saved_music_list.domain.models.SavedTracksModel

class SavedMusicDatasource {
    fun getTracks(context: Context): List<SavedTracksModel> {
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )

        return context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            "${MediaStore.Audio.Media.IS_MUSIC} != 0",
            null,
            "${MediaStore.Audio.Media.TITLE} ASC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            generateSequence { if (cursor.moveToNext()) cursor else null }
                .map {
                    SavedTracksModel(
                        id = it.getLong(idColumn),
                        title = it.getString(titleColumn),
                        artist = it.getString(artistColumn),
                        duration = it.getLong(durationColumn),
                        preview = it.getString(dataColumn),
                        uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, it.getLong(idColumn)))
                }
                .toList()
        } ?: emptyList()
    }
}