package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.networkresult.ApiResult

class GetMusicFeedUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(): ApiResult<List<Session>> {
        return repository.getFeed()
    }
}