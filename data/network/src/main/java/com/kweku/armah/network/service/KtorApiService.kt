package com.kweku.armah.network.service

import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.network.entity.SearchRequest
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.handleKtorApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorApiService @Inject constructor(private val client: HttpClient) : ApiService {
    override suspend fun getNetworkFeed(): ApiResult<FeedDto> {
        return handleKtorApi {
            client.get ("https://www.mocky.io/v2/5df79a3a320000f0612e01154sdfgsdfgsdfgsdfgsfgsdfgsgs")
        }
    }

    override suspend fun searchFeed(searchRequest: SearchRequest): ApiResult<FeedDto> {
        return handleKtorApi {
            client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = EndPoints.BASE_URL
                    path(EndPoints.SEARCH)
                }
            }
        }
    }
}