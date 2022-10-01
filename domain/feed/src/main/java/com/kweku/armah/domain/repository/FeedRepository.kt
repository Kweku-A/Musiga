package com.kweku.armah.domain.repository

import androidx.paging.PagingData
import com.kweku.armah.domain.model.Session
import com.kweku.armah.networkresult.ApiResult
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    suspend fun getFeedDto(): ApiResult<Unit>

    fun getLocalFeed(): Flow<PagingData<Session>>
}