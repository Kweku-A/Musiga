package com.kweku.armah.domain.usecase

import androidx.paging.PagingData
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetFeedUseCase(private val repository: FeedRepository) {

     operator fun invoke(): Flow<PagingData<Session>> {
        //repository.getFeedDto()
        return repository.getLocalFeed()
    }
}