package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSession1
import com.kweku.armah.domain.repository.fake.FakeSearchFeedRepository
import com.kweku.armah.networkresult.ApiErrorType
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchFeedUseCaseTest {

    private lateinit var repository: FakeSearchFeedRepository
    private lateinit var sut: SearchFeedUseCase

    @Before
    fun init() {
        repository = FakeSearchFeedRepository()
        sut = SearchFeedUseCase(repository = repository)
    }

    @Test
    fun should_return_list_of_sessions_with_searched_items() {
        val expected = listOf(fakeSession1)
        repository.response = ApiSuccess(expected)

        val result = runBlocking { sut() }
        assert(result is ApiSuccess)

        val actual = (result as ApiSuccess).data
        assertEquals(expected, actual)
    }

    @Test
    fun should_return_empty_list_request_failure() {
        val expectedResult = ApiError<List<Session>>(ApiErrorType.NETWORK_ERROR)
        repository.response = expectedResult
        val expected = ApiErrorType.NETWORK_ERROR

        val result = runBlocking { sut() }
        assert(result is ApiError)

        val actual = (result as ApiError).type
        assertEquals(expected, actual)
    }
}