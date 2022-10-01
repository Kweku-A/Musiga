package com.kweku.armah.database.dao.fake

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kweku.armah.database.entity.feed.FeedSessionEntity

class FakePagingSource(private val itemList: List<FeedSessionEntity>, private val refreshKey:Int) :
    PagingSource<Int, FeedSessionEntity>() {

    override fun getRefreshKey(state: PagingState<Int, FeedSessionEntity>): Int {
        return refreshKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedSessionEntity> {
        return LoadResult.Page(
            data = itemList.toList(), prevKey = null, nextKey = 2
        )
    }

}