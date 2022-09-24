package com.kweku.armah.network.service

import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.network.entity.SearchRequest
import com.kweku.armah.networkresult.ApiResult

interface ApiService {
    suspend fun getNetworkFeed(): ApiResult<FeedDto>
    suspend fun searchFeed(searchRequest: SearchRequest): ApiResult<FeedDto>
}