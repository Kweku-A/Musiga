package com.kweku.armah.database.entity.feed

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_session")
data class FeedSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @Embedded
    val currentTrackEntity: CurrentTrackEntity,
    val genres: List<String>,
    val listenerCount: Int,
    val name: String
)
