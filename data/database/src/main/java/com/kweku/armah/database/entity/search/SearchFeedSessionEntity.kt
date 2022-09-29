package com.kweku.armah.database.entity.search

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_feed_session")
data class SearchFeedSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @Embedded
    val searchCurrentTrackEntity: SearchCurrentTrackEntity,
    val genres: List<String>,
    val listenerCount: Int,
    val name: String
)
