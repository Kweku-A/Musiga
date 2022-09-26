package com.kweku.armah.domain.repository

import com.kweku.armah.domain.model.Session
import com.kweku.armah.networkresult.ApiResult
import kotlinx.coroutines.flow.StateFlow

interface SearchFeedRepository {
    suspend fun getSearchedFeedDto(): ApiResult<Unit>
    suspend fun getLocalSearchFeed(searchParams: String): StateFlow<List<Session>>
    suspend fun deleteSearchLocalFeed(): Boolean
}