package com.kweku.armah.network.di

import com.kweku.armah.network.client.contract.KtorClient
import com.kweku.armah.network.client.KtorClientImpl
import com.kweku.armah.network.datasource.contract.FeedDatasource
import com.kweku.armah.network.datasource.FeedDatasourceImpl
import com.kweku.armah.network.service.contract.ApiService
import com.kweku.armah.network.service.KtorApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideKtorClient(): KtorClient {
        return KtorClientImpl(CIO)
    }

    @Singleton
    @Provides
    fun provideHttpClientClient(ktorClient: KtorClient): HttpClient {
        return ktorClient.getKtorClient()
    }

    @Singleton
    @Provides
    fun provideApiService(client: HttpClient): ApiService {
        return KtorApiService(client)
    }

    @Singleton
    @Provides
    fun provideDatasource(apiService: ApiService, dispatcher: CoroutineDispatcher): FeedDatasource {
        return FeedDatasourceImpl(apiService, dispatcher)
    }
}
