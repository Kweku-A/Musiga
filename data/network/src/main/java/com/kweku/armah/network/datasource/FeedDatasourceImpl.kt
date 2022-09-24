package com.kweku.armah.network.datasource

import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.network.entity.SearchRequest
import com.kweku.armah.network.service.ApiService
import com.kweku.armah.networkresult.ApiResult
import javax.inject.Inject

class FeedDatasourceImpl @Inject constructor(private val apiService: ApiService) : FeedDatasource {

    override suspend fun getFeed(): ApiResult<FeedDto> {
        return apiService.getNetworkFeed()
    }

    override suspend fun searchFeed(request: SearchRequest): ApiResult<FeedDto> {
        return apiService.searchFeed(request)
    }
}