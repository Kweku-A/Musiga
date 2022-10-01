package com.kweku.armah.repository.di

import com.kweku.armah.database.db.MusigaDatabase
import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.network.datasource.contract.FeedDatasource
import com.kweku.armah.repository.feed.FeedRepositoryImpl
import com.kweku.armah.repository.search.SearchFeedRepositoryImpl
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
    fun providesFeedRepository(
        feedDatasource: FeedDatasource,
        musigaDatabase: MusigaDatabase,
    ): FeedRepository {
        return FeedRepositoryImpl(
            feedDatasource = feedDatasource,
            musigaDatabase = musigaDatabase
        )
    }

    @Singleton
    @Provides
    fun providesSearchFeedRepository(
        feedDatasource: FeedDatasource
    ): SearchFeedRepository {
        return SearchFeedRepositoryImpl(
            feedDatasource = feedDatasource
        )
    }
}