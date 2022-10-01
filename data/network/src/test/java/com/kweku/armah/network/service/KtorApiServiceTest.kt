package com.kweku.armah.network.service

import com.kweku.armah.network.client.mock.KtorClientMock
import com.kweku.armah.network.service.contract.ApiService
import com.kweku.armah.network.service.mock.MockApi
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class KtorApiServiceTest {

    private lateinit var sut: ApiService
    private lateinit var client: HttpClient

    @Before
    fun init() {
        client = KtorClientMock(MockApi()).getKtorClient()
        sut = KtorApiService(client)
    }

    @Test
    fun should_return_success_with_feed_dto() {
        val actualApiResult = runBlocking { sut.getNetworkFeed() }
        assertTrue(actualApiResult is ApiSuccess)
    }

    @Test
    fun should_return_success_with_feed_dto_when_searched() {
        val actualApiResult = runBlocking { sut.searchFeed() }
        assertTrue(actualApiResult is ApiSuccess)
    }
}