package com.kweku.armah.domain.repository.fake

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSessions
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.networkresult.ApiResult.ApiError
import com.kweku.armah.networkresult.ApiResult.ApiSuccess

class FakeSearchFeedRepository : SearchFeedRepository {

    var response: ApiResult<List<Session>> = ApiSuccess(fakeSessions)

    override suspend fun getSearchedFeedDto(): ApiResult<List<Session>> {
        val result = when (response) {
            is ApiSuccess -> {
                ApiSuccess((response as ApiSuccess<List<Session>>).data)
            }

            is ApiError -> {
                val errorType = (response as ApiError<List<Session>>).type
                ApiError(errorType)
            }
        }
        return result
    }
}