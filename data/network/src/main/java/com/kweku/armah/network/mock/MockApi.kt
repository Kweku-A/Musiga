package com.kweku.armah.network.mock

import com.kweku.armah.network.mock.MockApiResponse.FEED_RESPONSE
import com.kweku.armah.network.service.EndPoints
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import javax.inject.Inject

class MockApi @Inject constructor() {
    val engine = MockEngine { request ->
        when (request.url.encodedPath) {
            "${EndPoints.BASE_URL}${EndPoints.FEED}" -> {
                respond(
                    content = FEED_RESPONSE,
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }

            "${EndPoints.BASE_URL}${EndPoints.SEARCH}" -> {
                respond(
                    content = FEED_RESPONSE,
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }

            else -> {
                respond(
                    content = "",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
        }
    }
}
