package com.kweku.armah.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kweku.armah.database.entity.search.SearchFeedSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchFeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchFeedEntities(sessionEntities: List<SearchFeedSessionEntity>)

    @Query("SELECT * FROM search_feed_session WHERE " +
            "name COLLATE NOCASE LIKE :searchParams || '%' " +
            "OR title COLLATE NOCASE LIKE :searchParams || '%' " +
            "OR genres COLLATE NOCASE LIKE '%' || :searchParams || '%'"
    )
    fun getSearchFeedEntities(searchParams: String): Flow<List<SearchFeedSessionEntity>>

    @Query("SELECT Count(id) FROM feed_session")
    fun getSearchFeedCount(): Int

    @Query("DELETE FROM search_feed_session")
    fun deleteAllSearchFeedEntities()
}
