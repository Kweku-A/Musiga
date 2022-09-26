package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.repository.SearchFeedRepository

class DeleteLocalSearchFeedUsesCase(private val repository: SearchFeedRepository) {

    suspend operator fun invoke(): Boolean {
        return repository.deleteSearchLocalFeed()
    }
}