package com.kweku.armah.musiga.di

import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.domain.usecase.GetMusicFeedUseCase
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
    fun providesGetFeedUseCase(repository: FeedRepository): GetMusicFeedUseCase {
        return GetMusicFeedUseCase(repository)
    }
}