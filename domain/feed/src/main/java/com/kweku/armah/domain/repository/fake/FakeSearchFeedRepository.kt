package com.kweku.armah.domain.repository.fake

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSessions
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

class FakeSearchFeedRepository : SearchFeedRepository {

    var response: ApiResult<List<Session>> = ApiSuccess(fakeSessions)
    private var _sessionsFlow: MutableStateFlow<List<Session>> = MutableStateFlow(listOf())
    private val sessionsFlow: StateFlow<List<Session>> = _sessionsFlow

    override suspend fun getSearchedFeedDto(): ApiResult<Unit> {
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

    override suspend fun getLocalSearchFeed(searchParams: String): StateFlow<List<Session>> {
        return sessionsFlow
    }

    override suspend fun deleteSearchLocalFeed(): Boolean {
        _sessionsFlow.value = emptyList()
        return sessionsFlow.first().isEmpty()
    }
}