package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.networkresult.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class SearchFeedUseCase(
    private val repository: SearchFeedRepository,
    private val coroutineScope: CoroutineScope
) {

    suspend operator fun invoke(): ApiResult<List<Session>> {
        val async = coroutineScope.async {
            repository.getSearchedFeedDto()
        }
        return async.await()
    }
}