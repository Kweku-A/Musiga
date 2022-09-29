package com.kweku.armah.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kweku.armah.database.converter.FeedTypeConverter
import com.kweku.armah.database.dao.FeedDao
import com.kweku.armah.database.dao.RemoteKeysDao
import com.kweku.armah.database.dao.SearchFeedDao
import com.kweku.armah.database.entity.feed.FeedSessionEntity
import com.kweku.armah.database.entity.keys.RemoteKeys
import com.kweku.armah.database.entity.search.SearchFeedSessionEntity

@Database(entities = [FeedSessionEntity::class, SearchFeedSessionEntity::class, RemoteKeys::class], version = 1, exportSchema = true)
@TypeConverters(FeedTypeConverter::class)
abstract class MusigaDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao
    abstract fun searchFeedDao(): SearchFeedDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
