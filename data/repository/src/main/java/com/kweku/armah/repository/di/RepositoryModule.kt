package com.kweku.armah.repository.di

import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.network.datasource.FeedDatasource
import com.kweku.armah.repository.FeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesFeedRepository(feedDatasource: FeedDatasource): FeedRepository {
        return FeedRepositoryImpl(feedDatasource)
    }
}