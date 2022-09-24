package com.kweku.armah.network.datasource

import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.network.entity.SearchRequest
import com.kweku.armah.network.entity.SessionDto
import com.kweku.armah.networkresult.ApiResult

interface FeedDatasource {
    suspend fun getFeed(): ApiResult<FeedDto>
    suspend fun searchFeed(request: SearchRequest): ApiResult<FeedDto>
}