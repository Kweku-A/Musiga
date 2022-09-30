package com.kweku.armah.repository

import com.kweku.armah.database.dao.SearchFeedDao
import com.kweku.armah.domain.model.CurrentTrack
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.network.datasource.FeedDatasource
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SearchFeedRepositoryImpl @Inject constructor(
    private val feedDatasource: FeedDatasource,
    private val searchFeedDao: SearchFeedDao,
    private val coroutineScope: CoroutineScope

) : SearchFeedRepository {

    override suspend fun getSearchedFeedDto(): ApiResult<List<Session>> {

        return when (val response = feedDatasource.searchFeed()) {
            is ApiSuccess -> {
                val sessions = response.data.responseDataDto.sessionDtos
                val entities = sessions.map {
                    Session(
                        currentTrack = CurrentTrack(
                            artworkUrl = it.currentTrackDto.artworkUrl,
                            title = it.currentTrackDto.title
                        ),
                        genres = it.genres,
                        listenerCount = it.listenerCount,
                        name = it.name
                    )
                }
                ApiSuccess(entities)
            }

            is ApiError -> {
                ApiError(response.type)
            }
        }
    }
}