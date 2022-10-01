package com.kweku.armah.repository

import com.kweku.armah.domain.model.CurrentTrack
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.network.datasource.fake.FakeFeedDatasource
import com.kweku.armah.network.entity.fake.fakeFeedDto
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.repository.search.SearchFeedRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchFeedRepositoryTest {

    private lateinit var sut: SearchFeedRepository
    private lateinit var feedDatasource: FakeFeedDatasource

    @Before
    fun init() {
        feedDatasource = FakeFeedDatasource()
        sut = SearchFeedRepositoryImpl(feedDatasource = feedDatasource)
    }

    @Test
    fun should_return_searched_feed_session() {

        val expected = with(fakeFeedDto.responseDataDto.sessionDtos){
            this.map {
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
        }

        val response = runBlocking { sut.getSearchedFeedDto() }
        assert(response is ApiResult.ApiSuccess)
        val actual = (response as ApiResult.ApiSuccess).data
        assertEquals(expected, actual)
    }
}