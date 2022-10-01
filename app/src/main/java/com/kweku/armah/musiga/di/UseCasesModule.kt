package com.kweku.armah.musiga.di

import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.domain.usecase.FeedUseCases
import com.kweku.armah.domain.usecase.GetFeedUseCase
import com.kweku.armah.domain.usecase.SearchFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun providesGetFeedUseCase(repository: FeedRepository): GetFeedUseCase {
        return GetFeedUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesSearchFeedUseCase(
        repository: SearchFeedRepository,
    ): SearchFeedUseCase {
        return SearchFeedUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFeedUseCases(
        getFeedUseCase: GetFeedUseCase,
        searchFeedUseCase: SearchFeedUseCase,
    ) = FeedUseCases(
        searchFeedUseCase,
        getFeedUseCase
    )
}
