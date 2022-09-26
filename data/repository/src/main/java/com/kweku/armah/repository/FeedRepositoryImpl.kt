package com.kweku.armah.repository

import com.kweku.armah.database.dao.FeedDao
import com.kweku.armah.database.entity.feed.CurrentTrackEntity
import com.kweku.armah.database.entity.feed.FeedSessionEntity
import com.kweku.armah.domain.model.CurrentTrack
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.network.datasource.FeedDatasource
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDatasource: FeedDatasource,
    private val feedDao: FeedDao,
    private val coroutineScope: CoroutineScope

) : FeedRepository {

    override suspend fun getFeedDto(): ApiResult<Unit> {
        return when (val response = feedDatasource.getFeedUpdate()) {
            is ApiSuccess -> {
                val sessions = response.data.responseDataDto.sessionDtos
                val entities = sessions.map {
                    FeedSessionEntity(
                        currentTrackEntity = CurrentTrackEntity(
                            artworkUrl = it.currentTrackDto.artworkUrl,
                            title = it.currentTrackDto.title
                        ),
                        genres = it.genres,
                        listenerCount = it.listenerCount,
                        name = it.name
                    )
                }
                feedDao.insertFeedEntities(entities)
                ApiSuccess(Unit)
            }

            is ApiError -> {
                ApiError(response.type)
            }
        }
    }

    override suspend fun getLocalFeed(limit:Int,offset:Int): StateFlow<List<Session>> {
        return feedDao.getFeedEntities(limit, offset).map { entities ->
            entities.map {
                Session(
                    currentTrack = CurrentTrack(
                        artworkUrl = it.currentTrackEntity.artworkUrl,
                        title = it.currentTrackEntity.title
                    ),
                    genres = it.genres,
                    listenerCount = it.listenerCount,
                    name = it.name
                )
            }
        }.stateIn(coroutineScope)
    }

    override suspend fun deleteLocalFeed(): Boolean {
        feedDao.deleteAllFeedEntities()
        return feedDao.getFeedEntities().first().isEmpty()
    }
}