package com.kweku.armah.network.datasource.fake

import com.kweku.armah.network.datasource.contract.FeedDatasource
import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.network.entity.fake.fakeFeedDto
import com.kweku.armah.networkresult.ApiResult
import javax.inject.Inject

class FakeFeedDatasource @Inject constructor() : FeedDatasource {

    var response: ApiResult<FeedDto> = ApiResult.ApiSuccess(fakeFeedDto)

    override suspend fun getFeedUpdate(): ApiResult<FeedDto> {
        return response
    }

    override suspend fun searchFeed(): ApiResult<FeedDto> {
        return response
    }
}