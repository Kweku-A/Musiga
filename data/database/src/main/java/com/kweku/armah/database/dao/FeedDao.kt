package com.kweku.armah.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kweku.armah.database.entity.feed.FeedSessionEntity

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeedEntities(sessionEntities: List<FeedSessionEntity>)

    @Query("SELECT * FROM feed_session")
    fun getFeedEntities(): PagingSource<Int, FeedSessionEntity>

    @Query("SELECT id FROM feed_session ORDER BY id DESC LIMIT 1")
    fun getLastFeedId(): Long

    @Query("SELECT Count(id) FROM feed_session")
     fun getFeedCount(): Int

    @Query("DELETE FROM feed_session")
    suspend fun deleteAllFeedEntities()
}
