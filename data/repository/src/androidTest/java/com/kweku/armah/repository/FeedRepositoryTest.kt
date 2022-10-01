package com.kweku.armah.repository

import com.kweku.armah.database.db.MusigaDatabase
import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.network.datasource.fake.FakeFeedDatasource
import com.kweku.armah.network.entity.fake.fakeFeedDto
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import com.kweku.armah.repository.feed.FeedRepositoryImpl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class FeedRepositoryTest {

    @Inject
    lateinit var feedDatasource: FakeFeedDatasource

    @Inject
    lateinit var musigaDatabase: MusigaDatabase

    lateinit var sut: FeedRepository

    @get:Rule
    val hiltRule = HiltAndroidRule(this)


    @Before
    fun init() {
        hiltRule.inject()
        sut = FeedRepositoryImpl(feedDatasource, musigaDatabase)
    }

    @Test
    fun should_fetch_feed_dto_and_insert_into_db() {

        val expected = fakeFeedDto.responseDataDto.sessionDtos.size
        val result = runBlocking { sut.getFeedDto() }
        assert(result is ApiSuccess)

        val actual = musigaDatabase.feedDao().getFeedCount()
        assertEquals(expected, actual)

    }

}