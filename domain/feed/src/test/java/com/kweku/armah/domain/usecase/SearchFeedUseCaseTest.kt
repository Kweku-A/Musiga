package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSession1
import com.kweku.armah.domain.repository.fake.FakeSearchFeedRepository
import com.kweku.armah.networkresult.ApiErrorType
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchFeedUseCaseTest {

    private lateinit var repository: FakeSearchFeedRepository
    private lateinit var sut: SearchFeedUseCase
    private lateinit var coroutineScope: CoroutineScope

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        coroutineScope = CoroutineScope(StandardTestDispatcher())
        repository = FakeSearchFeedRepository()
        sut = SearchFeedUseCase(repository = repository, coroutineScope = coroutineScope)
    }

    @Test
    fun should_return_list_of_sessions_with_searched_items() {
        val expected = listOf(fakeSession1)
        repository.response = ApiSuccess(expected)

        val actual = runBlocking { sut(fakeSession1.name).first() }
        assertEquals(expected, actual)
    }

    @Test
    fun should_return_empty_list_request_failure() {
        val expectedResult = ApiError<List<Session>>(ApiErrorType.NOT_FOUND)
        repository.response = expectedResult
        val expected = emptyList<Session>()

        val actual = runBlocking { sut(fakeSession1.name).first() }
        assertEquals(expected, actual)
    }
}