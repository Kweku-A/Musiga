package com.kweku.armah.domain.usecase

data class FeedUseCases(
    val searchFeedUseCase: SearchFeedUseCase,
    val getFeedUseCase: GetFeedUseCase,
    val deleteLocalSearchFeedUsesCase: DeleteLocalSearchFeedUsesCase
)
