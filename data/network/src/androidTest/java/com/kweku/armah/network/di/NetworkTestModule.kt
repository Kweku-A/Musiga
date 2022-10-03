package com.kweku.armah.network.di

import com.kweku.armah.network.client.contract.KtorClient
import com.kweku.armah.network.client.mock.KtorClientMock
import com.kweku.armah.network.datasource.contract.FeedDatasource
import com.kweku.armah.network.datasource.FeedDatasourceImpl
import com.kweku.armah.network.service.contract.ApiService
import com.kweku.armah.network.service.KtorApiService
import com.kweku.armah.network.service.mock.MockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkTestModule::class]
)
object NetworkTestModule {

    @Singleton
    @Provides
    fun provideKtorClient(mockApi: MockApi): KtorClient {
        return KtorClientMock(mockApi)
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
