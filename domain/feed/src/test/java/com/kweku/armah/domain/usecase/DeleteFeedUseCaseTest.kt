package com.kweku.armah.domain.usecase

import com.kweku.armah.domain.model.fake.fakeSessions
import com.kweku.armah.domain.repository.fake.FakeFeedRepository
import com.kweku.armah.networkresult.ApiResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteFeedUseCaseTest {
    private lateinit var repository: FakeFeedRepository
    private lateinit var sut: DeleteLocalFeedUseCase
    private lateinit var getFeedUseCase: GetFeedUseCase

    @Before
    fun init() {
        repository = FakeFeedRepository()
        sut = DeleteLocalFeedUseCase(repository = repository)
        getFeedUseCase = GetFeedUseCase(repository = repository)
    }

    @Test
    fun should_return_empty_list_when_deleted() {
        repository.response = ApiResult.ApiSuccess(fakeSessions)
        val initial = runBlocking { getFeedUseCase().first() }
        assert(initial.isNotEmpty())

        val isEmpty = runBlocking { sut() }
        assert(isEmpty)
    }
}