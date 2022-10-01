package com.kweku.armah.database.dao.fake

import androidx.paging.PagingSource
import com.kweku.armah.database.dao.FeedDao
import com.kweku.armah.database.entity.feed.FeedSessionEntity

class FakeFeedDao : FeedDao {

    var itemList: MutableList<FeedSessionEntity> = mutableListOf()

    override fun insertFeedEntities(sessionEntities: List<FeedSessionEntity>) {
        itemList.addAll(sessionEntities)
    }

    override fun getFeedEntities(): PagingSource<Int, FeedSessionEntity> {
        return FakePagingSource(itemList, getLastFeedId().toInt())
    }

    override fun getLastFeedId(): Long {
        itemList.sortBy {
            it.id
        }
        return itemList.last().id
    }

    override fun getFeedCount(): Int {
        return itemList.size
    }

    override suspend fun deleteAllFeedEntities() {
        itemList.clear()
    }
}