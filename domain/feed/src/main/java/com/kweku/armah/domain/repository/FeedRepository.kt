package com.kweku.armah.domain.repository

import com.kweku.armah.domain.model.Session
import com.kweku.armah.networkresult.ApiResult

interface FeedRepository {
    suspend fun getFeed(): ApiResult<List<Session>>
    suspend fun getSearchedFeed(searchParam: String): ApiResult<List<Session>>
}