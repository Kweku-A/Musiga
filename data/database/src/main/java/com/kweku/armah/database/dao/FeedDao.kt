package com.kweku.armah.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kweku.armah.database.entity.feed.FeedSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeedEntities(sessionEntities: List<FeedSessionEntity>)

    @Query("SELECT * FROM feed_session ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getFeedEntities(limit:Int,offset:Int): Flow<List<FeedSessionEntity>>

    @Query("DELETE FROM feed_session")
    fun deleteAllFeedEntities()
}
