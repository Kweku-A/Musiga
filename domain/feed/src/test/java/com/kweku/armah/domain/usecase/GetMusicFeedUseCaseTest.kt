package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSessions
import com.kweku.armah.domain.repository.fake.FakeFeedRepository
import com.kweku.armah.networkresult.ApiErrorType
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMusicFeedUseCaseTest {
    private lateinit var repository: FakeFeedRepository
    private lateinit var sut: GetMusicFeedUseCase

    @Before
    fun init() {
        repository = FakeFeedRepository()
        sut = GetMusicFeedUseCase(repository = repository)
    }

    @Test
    fun should_return_list_of_sessions() {
        val expectedApiResult = ApiSuccess(fakeSessions)
        val actualApiResult = runBlocking { sut() }
        assertEquals(expectedApiResult, actualApiResult)

        val expected = fakeSessions
        val actual = (actualApiResult as ApiSuccess).data
        assertEquals(expected, actual)
    }

    @Test
    fun should_return_not_found_type_on_request_failure() {
        val expectedResult = ApiError<List<Session>>(ApiErrorType.NOT_FOUND)
        repository.response = expectedResult
        val expected = expectedResult.type

        val result = runBlocking { sut() }
        val actual = (result as ApiError).type
        assertEquals(expected, actual)
    }

    @Test
    fun should_return_exception_on_network_exception() {
        val expectedResult = ApiError<List<Session>>(ApiErrorType.EXCEPTION)
        repository.response = expectedResult
        val expected = expectedResult.type

        val result = runBlocking { sut() }
        val actual = (result as ApiError).type
        assertEquals(expected, actual)
    }
}