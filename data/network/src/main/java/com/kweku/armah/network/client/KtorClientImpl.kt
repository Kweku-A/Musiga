package com.kweku.armah.network.client

import android.util.Log
import com.kweku.armah.network.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject

class KtorClientImpl @Inject constructor(
    private val engineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
) : KtorClient {

    override fun getKtorClient(): HttpClient {
        return HttpClient(engineFactory) {
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }

            Logging {
                if (BuildConfig.DEBUG) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.v("Logger Ktor =>", message)
                        }
                    }
                    level = LogLevel.ALL
                }
            }

            ResponseObserver { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }

            defaultRequest {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}
