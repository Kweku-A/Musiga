package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.FeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetFeedUseCase(private val repository: FeedRepository) {

    suspend operator fun invoke(limit: Int, offset: Int): StateFlow<List<Session>> {
        repository.getFeedDto()
        return repository.getLocalFeed(limit, offset)
    }
}