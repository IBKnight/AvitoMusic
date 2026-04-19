package com.avito.avitomusic.features.saved_music.data.repository


import com.avito.avitomusic.common.data.model.ApiTrack
import com.avito.avitomusic.features.music_list.data.models.TrackModel
import com.avito.avitomusic.features.music_player.data.models.TrackListItemModel
import com.avito.avitomusic.features.saved_music.data.SavedTrackDao
import com.avito.avitomusic.features.saved_music.data.model.SavedTrackModel
import com.avito.avitomusic.features.saved_music.domain.repository.ISavedMusicRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SavedMusicRepoImpl @Inject constructor(
    private val dao: SavedTrackDao
) : ISavedMusicRepo {
    override suspend fun getSavedTracks(): Flow<List<SavedTrackModel>> =
        dao.getAll().map { entities ->
            entities.map { entity ->
                SavedTrackModel(
                    id = entity.id,
                    title = entity.title,
                    duration = entity.duration,
                    artist = entity.artist,
                    artistId = entity.artistId,
                    preview = entity.preview
                )
            }
        }

    override suspend fun saveTrack(track: SavedTrackModel) =
        withContext(Dispatchers.IO) {
            dao.insert(track)
        }

    override suspend fun removeTrack(track: SavedTrackModel) =
        withContext(Dispatchers.IO) {
            dao.delete(track)
        }

    override suspend fun isTrackSaved(id: Long): Boolean =
        withContext(Dispatchers.IO) {
            dao.isSaved(id)
        }
}


private fun getArtistId(track: ApiTrack): Long {
    return when (track) {
        is TrackModel -> track.artist.id
        is TrackListItemModel -> track.artist.id
        else -> 0L
    }
}

private fun getArtistName(track: ApiTrack): String {
    return when (track) {
        is TrackModel -> track.artist.name
        is TrackListItemModel -> track.artist.name
        else -> "<unknown>"
    }
}
