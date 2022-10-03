package com.kweku.armah.network.datasource

import com.kweku.armah.network.datasource.contract.FeedDatasource
import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.network.service.contract.ApiService
import com.kweku.armah.networkresult.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedDatasourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) : FeedDatasource {

    override suspend fun getFeedUpdate(): ApiResult<FeedDto> {
        return withContext(dispatcher) {
            delay(10000) // delay to allow showing of circular progress
            apiService.getNetworkFeed()
        }
    }

    override suspend fun searchFeed(): ApiResult<FeedDto> {
        return withContext(dispatcher) {
            delay(2000) // delay to allow showing of circular progress
            apiService.searchFeed()
        }
    }
}
