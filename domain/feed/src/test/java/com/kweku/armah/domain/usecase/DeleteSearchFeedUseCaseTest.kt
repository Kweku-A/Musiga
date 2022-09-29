package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.fake.fakeSession1
import com.kweku.armah.domain.repository.fake.FakeSearchFeedRepository
import com.kweku.armah.networkresult.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

class DeleteSearchFeedUseCaseTest {
    private lateinit var repository: FakeSearchFeedRepository
    private lateinit var sut: DeleteLocalSearchFeedUsesCase
    private lateinit var searchFeedUseCase: SearchFeedUseCase
    private lateinit var coroutineScope: CoroutineScope

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        coroutineScope = CoroutineScope(UnconfinedTestDispatcher())
        repository = FakeSearchFeedRepository()
        sut =
            DeleteLocalSearchFeedUsesCase(repository = repository, coroutineScope = coroutineScope)
        searchFeedUseCase =
            SearchFeedUseCase(repository = repository, coroutineScope = coroutineScope)
    }

    @Test
    fun should_return_empty_list_when_deleted()  {
        val list = listOf(fakeSession1)
        repository.response = ApiResult.ApiSuccess(list)
        val initial =  runBlocking { searchFeedUseCase(fakeSession1.name).first() }
        assert(initial.isNotEmpty())

        val isEmpty = runBlocking { sut() }
        assert(isEmpty)
    }
}