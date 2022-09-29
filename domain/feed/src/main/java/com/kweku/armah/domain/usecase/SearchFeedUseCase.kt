package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.SearchFeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class SearchFeedUseCase(
    private val repository: SearchFeedRepository,
    private val coroutineScope: CoroutineScope
) {

    suspend operator fun invoke(searchParams: String): Flow<List<Session>> {
        val async = coroutineScope.async {
            repository.getSearchedFeedDto()
        }
        async.await()
        return repository.getLocalSearchFeed(searchParams)
    }
}