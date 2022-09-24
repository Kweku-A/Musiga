package com.kweku.armah.repository

import com.kweku.armah.domain.model.CurrentTrack
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.network.datasource.FeedDatasource
import com.kweku.armah.network.entity.SearchRequest
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.mapResult
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(private val feedDatasource: FeedDatasource) :
    FeedRepository {

    override suspend fun getFeed(): ApiResult<List<Session>> {
        return feedDatasource.getFeed().mapResult { musicResponse ->
            val sessions = musicResponse.responseDataDto.sessionDtos
            sessions.map {
                Session(
                    currentTrack = CurrentTrack(
                        artworkUrl = it.currentTrackDto.artworkUrl,
                        title = it.currentTrackDto.title
                    ), genres = it.genres,
                    listenerCount = it.listenerCount,
                    name = it.name
                )
            }
        }
    }

    override suspend fun getSearchedFeed(searchParam: String): ApiResult<List<Session>> {
        val request = SearchRequest(searchParams = searchParam)
        return feedDatasource.searchFeed(request).mapResult { musicResponse ->
            val sessions = musicResponse.responseDataDto.sessionDtos
            sessions.map {
                Session(
                    currentTrack = CurrentTrack(
                        artworkUrl = it.currentTrackDto.artworkUrl,
                        title = it.currentTrackDto.title
                    ), genres = it.genres,
                    listenerCount = it.listenerCount,
                    name = it.name
                )
            }
        }
    }
}