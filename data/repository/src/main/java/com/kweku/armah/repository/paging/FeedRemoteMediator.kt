package com.kweku.armah.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kweku.armah.database.dao.FeedDao
import com.kweku.armah.database.dao.RemoteKeysDao
import com.kweku.armah.database.db.MusigaDatabase
import com.kweku.armah.database.entity.feed.CurrentTrackEntity
import com.kweku.armah.database.entity.feed.FeedSessionEntity
import com.kweku.armah.database.entity.keys.RemoteKeys
import com.kweku.armah.network.datasource.FeedDatasource
import com.kweku.armah.networkresult.ApiResult
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator @Inject constructor(
    private val feedDatasource: FeedDatasource,
    private val musigaDatabase: MusigaDatabase
) : RemoteMediator<Int, FeedSessionEntity>() {

    private val feedDao: FeedDao = musigaDatabase.feedDao()
    private val remoteKeysDao: RemoteKeysDao = musigaDatabase.remoteKeysDao()

    private var pageCount: Int = 0
    private var isEndOfPagination = false

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FeedSessionEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                pageCount = 0
                isEndOfPagination = false
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.previousKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return when (val response = feedDatasource.getFeedUpdate()) {
            is ApiResult.ApiSuccess -> {
                // Added to allow showing of progress dialog

                val sessions = response.data.responseDataDto.sessionDtos
                isEndOfPagination = sessions.isEmpty()

                pageCount++
                if (pageCount > 4) {
                    isEndOfPagination = true
                }

                val previousKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfPagination) null else page + 1

                val keys = sessions.map {
                    RemoteKeys(previousKey = previousKey, nextKey = nextKey)
                }
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

                musigaDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        feedDao.deleteAllFeedEntities()
                        remoteKeysDao.clearRemoteKeys()
                    }

                    remoteKeysDao.insertAll(keys)


                    feedDao.insertFeedEntities(entities)
                }
                MediatorResult.Success(endOfPaginationReached = isEndOfPagination)
            }

            is ApiResult.ApiError -> {
                MediatorResult.Error(Exception(response.type?.name))
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FeedSessionEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                remoteKeysDao.remoteKeysEntitiesId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FeedSessionEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                remoteKeysDao.remoteKeysEntitiesId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FeedSessionEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                remoteKeysDao.remoteKeysEntitiesId(repo.id)
            }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 10
    }
}