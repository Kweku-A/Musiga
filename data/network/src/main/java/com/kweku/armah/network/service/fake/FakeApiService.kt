package com.kweku.armah.network.service.fake

import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.network.entity.SearchRequest
import com.kweku.armah.network.service.ApiService
import com.kweku.armah.network.service.EndPoints
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.handleKtorApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path

class FakeApiService (private val client: HttpClient) :ApiService{
    override suspend fun getNetworkFeed(): ApiResult<FeedDto> {
        return handleKtorApi {
            client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    path(EndPoints.FEED)
                }
            }
        }
    }

    override suspend fun searchFeed(searchRequest: SearchRequest): ApiResult<FeedDto> {
        return handleKtorApi {
            client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    path(EndPoints.FEED)
                }
            }
        }
    }
}