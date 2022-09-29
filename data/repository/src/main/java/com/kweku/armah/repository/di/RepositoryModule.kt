package com.kweku.armah.repository.di

import com.kweku.armah.database.dao.FeedDao
import com.kweku.armah.database.dao.SearchFeedDao
import com.kweku.armah.database.db.MusigaDatabase
import com.kweku.armah.domain.repository.FeedRepository
import com.kweku.armah.domain.repository.SearchFeedRepository
import com.kweku.armah.network.datasource.FeedDatasource
import com.kweku.armah.repository.FeedRepositoryImpl
import com.kweku.armah.repository.SearchFeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesFeedRepository(
        feedDatasource: FeedDatasource,
        feedDao: FeedDao,
        musigaDatabase: MusigaDatabase,
        coroutineScope: CoroutineScope
    ): FeedRepository {
        return FeedRepositoryImpl(
            feedDatasource = feedDatasource,
            feedDao = feedDao,
            musigaDatabase = musigaDatabase
        )
    }

    @Singleton
    @Provides
    fun providesSearchFeedRepository(
        feedDatasource: FeedDatasource,
        searchFeedDao: SearchFeedDao,
        coroutineScope: CoroutineScope
    ): SearchFeedRepository {
        return SearchFeedRepositoryImpl(
            feedDatasource = feedDatasource,
            searchFeedDao = searchFeedDao,
            coroutineScope = coroutineScope
        )
    }
}