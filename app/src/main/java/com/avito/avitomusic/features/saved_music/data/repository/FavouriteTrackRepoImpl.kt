package com.avito.avitomusic.features.saved_music.data.repository


import com.avito.avitomusic.common.di.IoDispatcher
import com.avito.avitomusic.features.saved_music.data.FavouriteTrackDao
import com.avito.avitomusic.features.saved_music.data.model.FavouriteTrackModel
import com.avito.avitomusic.features.saved_music.domain.repository.IFavouriteMusicRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FavouriteTrackRepoImpl @Inject constructor(
    private val dao: FavouriteTrackDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IFavouriteMusicRepo {
    override suspend fun getSavedTracks(): Flow<List<FavouriteTrackModel>> =
        dao.getAll().map { entities ->
            entities.map { entity ->
                FavouriteTrackModel(
                    id = entity.id,
                    title = entity.title,
                    duration = entity.duration,
                    artist = entity.artist,
                    artistId = entity.artistId,
                    trackImage = entity.trackImage,
                    preview = entity.preview
                )
            }
        }

    override suspend fun saveTrack(track: FavouriteTrackModel) =
        withContext(ioDispatcher) {
            dao.insert(track)
        }

    override suspend fun removeTrack(track: FavouriteTrackModel) =
        withContext(ioDispatcher) {
            dao.delete(track)
        }

    override suspend fun isTrackSaved(id: Long): Boolean =
        withContext(ioDispatcher) {
            dao.isSaved(id)
        }

    override suspend fun search(query: String): Flow<List<FavouriteTrackModel>> =
        withContext(ioDispatcher) {
            dao.search(query)
        }
}
