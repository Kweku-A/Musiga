package com.kweku.armah.domain.repository

import com.kweku.armah.domain.model.Session
import com.kweku.armah.networkresult.ApiResult

interface SearchFeedRepository {
    suspend fun getSearchedFeedDto(): ApiResult<List<Session>>
}