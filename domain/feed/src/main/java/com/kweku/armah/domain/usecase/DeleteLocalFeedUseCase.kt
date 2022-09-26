package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.repository.FeedRepository

class DeleteLocalFeedUseCase (private val repository: FeedRepository){

    suspend operator fun invoke(): Boolean {
        return repository.deleteLocalFeed()
    }
}