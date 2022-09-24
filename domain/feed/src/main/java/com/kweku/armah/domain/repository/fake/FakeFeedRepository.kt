package com.kweku.armah.domain.repository.fake

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSessions
import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.ApiResult.ApiSuccess

class FakeFeedRepository : FeedRepository {

    var response:ApiResult<List<Session>> = ApiSuccess(fakeSessions)
    override suspend fun getFeed(): ApiResult<List<Session>> {
        return response
    }

    override suspend fun getSearchedFeed(searchParam: String): ApiResult<List<Session>> {
        return response
    }
}