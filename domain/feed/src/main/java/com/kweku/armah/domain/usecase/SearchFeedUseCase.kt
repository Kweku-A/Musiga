package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.SearchFeedRepository
import kotlinx.coroutines.flow.StateFlow

class SearchFeedUseCase(private val repository: SearchFeedRepository) {

    suspend operator fun invoke(searchParams: String): StateFlow<List<Session>> {
        repository.getSearchedFeedDto()
        return repository.getLocalSearchFeed(searchParams)
    }
}