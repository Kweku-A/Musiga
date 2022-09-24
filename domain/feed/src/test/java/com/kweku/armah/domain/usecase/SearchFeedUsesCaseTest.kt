package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSession1
import com.kweku.armah.domain.repository.fake.FakeFeedRepository
import com.kweku.armah.networkresult.ApiErrorType
import com.kweku.armah.networkresult.ApiResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchFeedUsesCaseTest {

    private lateinit var repository: FakeFeedRepository
    private lateinit var sut: SearchFeedUsesCase

    @Before
    fun init() {
        repository = FakeFeedRepository()
        sut = SearchFeedUsesCase(repository = repository)
    }

    @Test
    fun should_return_list_of_sessions_with_searched_items() {
        val expected = fakeSession1
        repository.response = ApiResult.ApiSuccess(listOf(expected))

        val actualApiResult = runBlocking { sut(fakeSession1.name) }
        val actual = (actualApiResult as ApiResult.ApiSuccess).data[0]
        assertEquals(expected, actual)
    }

    @Test
    fun should_return_not_found_type_on_request_failure(){
        val expectedResult = ApiResult.ApiError<List<Session>>(ApiErrorType.NOT_FOUND)
        repository.response = expectedResult
        val expected = expectedResult.type

        val result = runBlocking { sut(fakeSession1.name) }
        val actual = (result as ApiResult.ApiError).type
        assertEquals(expected, actual )
    }

    @Test
    fun should_return_exception_on_network_exception(){
        val expectedResult = ApiResult.ApiError<List<Session>>(ApiErrorType.EXCEPTION)
        repository.response = expectedResult
        val expected = expectedResult.type

        val result = runBlocking { sut(fakeSession1.name) }
        val actual = (result as ApiResult.ApiError).type
        assertEquals(expected, actual )
    }
}