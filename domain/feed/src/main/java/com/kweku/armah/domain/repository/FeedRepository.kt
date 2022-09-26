package com.kweku.armah.domain.repository

import com.kweku.armah.domain.model.Session
import com.kweku.armah.networkresult.ApiResult
import kotlinx.coroutines.flow.StateFlow

interface FeedRepository {
    suspend fun getFeedDto(): ApiResult<Unit>

    suspend fun getLocalFeed(limit:Int,offset:Int): StateFlow<List<Session>>

    suspend fun deleteLocalFeed(): Boolean
}