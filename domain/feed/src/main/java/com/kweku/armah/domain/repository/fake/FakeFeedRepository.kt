package com.kweku.armah.domain.repository.fake

import androidx.paging.PagingData
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSessions
import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

class FakeFeedRepository : FeedRepository {

    var response: ApiResult<List<Session>> = ApiSuccess(fakeSessions)
    private var _sessionsFlow: MutableStateFlow<List<Session>> = MutableStateFlow(listOf())

    private val sessionsFlow: StateFlow<List<Session>> = _sessionsFlow

    override suspend fun getFeedDto(): ApiResult<Unit> {
        val result = when (response) {
            is ApiSuccess -> {
                _sessionsFlow.value = (response as ApiSuccess<List<Session>>).data
                ApiSuccess(Unit)
            }

            is ApiError -> {
                val errorType = (response as ApiError<List<Session>>).type
                ApiError(errorType)
            }
        }
        return result
    }

    override fun getLocalFeed(): Flow<PagingData<Session>> {
        TODO("Not yet implemented")
    }

    /*override suspend fun getLocalFeed(): StateFlow<List<Session>> {
        return sessionsFlow
    }*/

    override suspend fun deleteLocalFeed(): Boolean {
        _sessionsFlow.value = emptyList()
        return sessionsFlow.first().isEmpty()
    }
}