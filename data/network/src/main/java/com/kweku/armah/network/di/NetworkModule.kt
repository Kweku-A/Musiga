package com.kweku.armah.network.di

import com.kweku.armah.network.client.KtorClientMock
import com.kweku.armah.network.datasource.FeedDatasource
import com.kweku.armah.network.datasource.FeedDatasourceImpl
import com.kweku.armah.network.service.ApiService
import com.kweku.armah.network.service.KtorApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    @Singleton
//    @Provides
//    fun provideKtorClient(): KtorClient{
//        return KtorClientImpl(CIO)
//    }

    @Singleton
    @Provides
    fun provideHttpClientClient(ktorClient: KtorClientMock): HttpClient {
        return ktorClient.getKtorClient()
    }

    @Singleton
    @Provides
    fun provideApiService(client: HttpClient): ApiService {
        return KtorApiService(client)
    }

    @Singleton
    @Provides
    fun provideDatasource(apiService: ApiService): FeedDatasource {
        return FeedDatasourceImpl(apiService)
    }
}