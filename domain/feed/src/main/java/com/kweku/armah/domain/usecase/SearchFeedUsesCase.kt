package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.repository.FeedRepository

class SearchFeedUsesCase(private val repository: FeedRepository) {
    suspend operator fun invoke(searchParams:String) = repository.getSearchedFeed(searchParams)
}