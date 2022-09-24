package com.kweku.armah.network.mock

import com.kweku.armah.network.mock.MockApiResponse.FEED_RESPONSE
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import javax.inject.Inject

class MockApi @Inject constructor(){
    val engine = MockEngine { request ->
        if(request.url.encodedPath == ""){
            respond(
                content = FEED_RESPONSE,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }else{
            respond(
                content = FEED_RESPONSE,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

    }
}