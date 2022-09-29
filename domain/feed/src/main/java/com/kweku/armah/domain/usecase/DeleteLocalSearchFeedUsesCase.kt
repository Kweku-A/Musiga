package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.repository.SearchFeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class DeleteLocalSearchFeedUsesCase(
    private val repository: SearchFeedRepository,
    private val coroutineScope: CoroutineScope
) {

    suspend operator fun invoke(): Boolean {
        val deleted = coroutineScope.async {
            repository.deleteSearchLocalFeed()
        }
        return deleted.await()
    }
}