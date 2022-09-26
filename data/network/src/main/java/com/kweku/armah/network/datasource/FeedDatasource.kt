package com.kweku.armah.network.datasource

import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.networkresult.ApiResult

interface FeedDatasource {
    suspend fun getFeedUpdate(): ApiResult<FeedDto>
    suspend fun searchFeed(): ApiResult<FeedDto>
}
