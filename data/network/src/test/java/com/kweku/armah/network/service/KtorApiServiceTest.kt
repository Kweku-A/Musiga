package com.kweku.armah.network.service

import com.kweku.armah.network.client.KtorClientImpl
import com.kweku.armah.network.entity.DataDto
import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.network.entity.SearchRequest
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class KtorApiServiceTest {

    private lateinit var sut: ApiService
    private lateinit var client: HttpClient

    @Before
    fun init() {
        client = KtorClientImpl().client
        sut = KtorApiService(client)
    }

    @Test
    fun should_return_success_with_feed_dto() = testApplication {

        val testHttpClient = createClient {
            install(HttpCookies)
            this@testApplication.install(ContentNegotiation) {
                json()
            }
        }

        sut = KtorApiService(testHttpClient)

        externalServices {
            hosts("/") {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }

                routing {
                    this.get(EndPoints.FEED) {
                        this.call.respond(FeedDto(responseDataDto = DataDto(sessionDtos = emptyList())))
                    }
                }
            }
        }

        val actualApiResult = runBlocking { sut.getNetworkFeed() }
        assertTrue(actualApiResult is ApiSuccess)
    }

    @Test
    fun should_return_success_with_feed_dto_when_searched() {
        val dummyRequest = SearchRequest(searchParams = "a")
        val actualApiResult = runBlocking { sut.searchFeed(dummyRequest) }
        assertTrue(actualApiResult is ApiSuccess)
    }
}