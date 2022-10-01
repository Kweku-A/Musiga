package com.kweku.armah.network.client.mock

import com.kweku.armah.network.client.contract.KtorClient
import com.kweku.armah.network.service.mock.MockApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorClientMock @Inject constructor(private val mockApi: MockApi) : KtorClient {

    override fun getKtorClient(): HttpClient {
        return HttpClient(mockApi.engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}
