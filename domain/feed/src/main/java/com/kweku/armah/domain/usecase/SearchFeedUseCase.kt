package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.networkresult.ApiResult

class SearchFeedUseCase(
    private val repository: SearchFeedRepository
) {

    suspend operator fun invoke(): ApiResult<List<Session>> {
        return repository.getSearchedFeedDto()
    }
}