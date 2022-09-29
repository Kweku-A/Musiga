package com.kweku.armah.musiga.di

import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.domain.usecase.DeleteLocalFeedUseCase
import com.kweku.armah.domain.usecase.DeleteLocalSearchFeedUsesCase
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
object AppModule {

    @Singleton
    @Provides
    fun providesGetFeedUseCase(repository: FeedRepository): GetFeedUseCase {
        return GetFeedUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesDeleteLocalFeedUseCase(repository: FeedRepository): DeleteLocalFeedUseCase {
        return DeleteLocalFeedUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesSearchFeedUseCase(repository: SearchFeedRepository): SearchFeedUseCase {
        return SearchFeedUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesDeleteLocalSearchFeedUsesCase(repository: SearchFeedRepository): DeleteLocalSearchFeedUsesCase {
        return DeleteLocalSearchFeedUsesCase(repository)
    }

    @Provides
    @Singleton
    fun provideFeedUseCases(
        getFeedUseCase: GetFeedUseCase,
        searchFeedUseCase: SearchFeedUseCase,
        deleteLocalFeedUseCase: DeleteLocalFeedUseCase,
        deleteLocalSearchFeedUsesCase: DeleteLocalSearchFeedUsesCase
    ) = FeedUseCases(
        searchFeedUseCase,
        getFeedUseCase,
        deleteLocalSearchFeedUsesCase,
        deleteLocalFeedUseCase
    )
}
