package com.kweku.armah.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kweku.armah.database.dao.FeedDao
import com.kweku.armah.database.db.MusigaDatabase
import com.kweku.armah.database.entity.feed.CurrentTrackEntity
import com.kweku.armah.database.entity.feed.FeedSessionEntity
import com.kweku.armah.domain.model.CurrentTrack
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.network.datasource.FeedDatasource
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import com.kweku.armah.repository.paging.FeedRemoteMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedDatasource: FeedDatasource,
    private val feedDao: FeedDao,
    private val musigaDatabase: MusigaDatabase,
    private val coroutineScope: CoroutineScope

) : FeedRepository {

    private var itemsCount = 0
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
                itemsCount = entities.size
                feedDao.insertFeedEntities(entities)
                ApiSuccess(Unit)
            }

            is ApiError -> {
                ApiError(response.type)
            }
        }
    }


    override fun getLocalFeed(): Flow<PagingData<Session>> {
        val pagingSourceFactory = { feedDao.getFeedEntities() }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = FeedRemoteMediator.PAGE_SIZE,
                initialLoadSize = FeedRemoteMediator.PAGE_SIZE * 2,
            ),
            remoteMediator = FeedRemoteMediator(
                feedDatasource = feedDatasource,
                musigaDatabase = musigaDatabase,
            ),
            initialKey = 1,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
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
        }
    }

    override suspend fun deleteLocalFeed(): Boolean {
        feedDao.deleteAllFeedEntities()
        return feedDao.getFeedCount() == 0
    }
}