package com.kweku.armah.network.service.contract

import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.networkresult.ApiResult

interface ApiService {
    suspend fun getNetworkFeed(): ApiResult<FeedDto>
    suspend fun searchFeed(): ApiResult<FeedDto>
}
