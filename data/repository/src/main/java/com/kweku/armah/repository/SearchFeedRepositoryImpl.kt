package com.kweku.armah.repository

import com.kweku.armah.database.dao.SearchFeedDao
import com.kweku.armah.database.entity.search.SearchCurrentTrackEntity
import com.kweku.armah.database.entity.search.SearchFeedSessionEntity
import com.kweku.armah.domain.model.CurrentTrack
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.SearchFeedRepository
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

class SearchFeedRepositoryImpl @Inject constructor(
    private val feedDatasource: FeedDatasource,
    private val searchFeedDao: SearchFeedDao,
    private val coroutineScope: CoroutineScope

) : SearchFeedRepository {

    override suspend fun getSearchedFeedDto(): ApiResult<Unit> {
        return when (val response = feedDatasource.searchFeed()) {
            is ApiSuccess -> {
                val sessions = response.data.responseDataDto.sessionDtos
                val entities = sessions.map {
                    SearchFeedSessionEntity(
                        searchCurrentTrackEntity = SearchCurrentTrackEntity(
                            artworkUrl = it.currentTrackDto.artworkUrl,
                            title = it.currentTrackDto.title
                        ),
                        genres = it.genres,
                        listenerCount = it.listenerCount,
                        name = it.name
                    )
                }
                searchFeedDao.insertSearchFeedEntities(entities)
                ApiSuccess(Unit)
            }

            is ApiError -> {
                ApiError(response.type)
            }
        }
    }

    override suspend fun getLocalSearchFeed(searchParams: String): StateFlow<List<Session>> {
        return searchFeedDao.getSearchFeedEntities(searchParams).map { entities ->
            entities.map {
                Session(
                    currentTrack = CurrentTrack(
                        artworkUrl = it.searchCurrentTrackEntity.artworkUrl,
                        title = it.searchCurrentTrackEntity.title
                    ),
                    genres = it.genres,
                    listenerCount = it.listenerCount,
                    name = it.name
                )
            }
        }.stateIn(coroutineScope)
    }

    override suspend fun deleteSearchLocalFeed(): Boolean {
        searchFeedDao.deleteAllSearchFeedEntities()
        return searchFeedDao.getAllSearchFeedEntities().first().isEmpty()
    }
}