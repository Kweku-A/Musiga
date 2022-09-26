package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.fake.fakeSession1
import com.kweku.armah.domain.repository.fake.FakeSearchFeedRepository
import com.kweku.armah.networkresult.ApiResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteSearchFeedUseCaseTest {
    private lateinit var repository: FakeSearchFeedRepository
    private lateinit var sut: DeleteLocalSearchFeedUsesCase
    private lateinit var searchFeedUseCase: SearchFeedUseCase

    @Before
    fun init() {
        repository = FakeSearchFeedRepository()
        sut = DeleteLocalSearchFeedUsesCase(repository = repository)
        searchFeedUseCase= SearchFeedUseCase(repository = repository)
    }

    @Test
    fun should_return_empty_list_when_deleted() {
        val list = listOf(fakeSession1)
        repository.response = ApiResult.ApiSuccess(list)
        val initial = runBlocking { searchFeedUseCase(fakeSession1.name).first() }
        assert(initial.isNotEmpty())

        val isEmpty = runBlocking { sut() }
        assert(isEmpty)
    }
}