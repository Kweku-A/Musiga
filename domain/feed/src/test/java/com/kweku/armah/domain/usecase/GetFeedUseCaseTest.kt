package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSessions
import com.kweku.armah.domain.repository.fake.FakeFeedRepository
import com.kweku.armah.networkresult.ApiErrorType
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFeedUseCaseTest {
    private lateinit var repository: FakeFeedRepository
    private lateinit var sut: GetFeedUseCase

    @Before
    fun init() {
        repository = FakeFeedRepository()
        sut = GetFeedUseCase(repository = repository)
    }

    @Test
    fun should_return_list_of_sessions() {
        val expected = fakeSessions
        repository.response = ApiSuccess(expected)
        val actual = runBlocking { sut().first() }
        assertEquals(expected, actual)
    }

    @Test
    fun should_return_empty_list_request_failure() {
        val expectedResult = ApiError<List<Session>>(ApiErrorType.NOT_FOUND)
        repository.response = expectedResult
        val expected = emptyList<Session>()

        val actual = runBlocking { sut().first() }
        assertEquals(expected, actual)
    }
}