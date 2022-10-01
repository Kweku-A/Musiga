package com.kweku.armah.database.db.fake

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.kweku.armah.database.dao.FeedDao
import com.kweku.armah.database.dao.RemoteKeysDao
import com.kweku.armah.database.dao.fake.FakeFeedDao
import com.kweku.armah.database.dao.fake.FakeRemoteKeysDao
import com.kweku.armah.database.db.MusigaDatabase

class FakeMusigaDatabase : MusigaDatabase() {

    private val feedDao = FakeFeedDao()
    private val remoteKeysDao = FakeRemoteKeysDao()

    override fun feedDao(): FeedDao {
        return feedDao
    }

    override fun remoteKeysDao(): RemoteKeysDao {
        return remoteKeysDao
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        throw UnsupportedOperationException("Not supported for unit test")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        throw UnsupportedOperationException("Not supported for unit test")
    }

    override fun clearAllTables() {
        throw UnsupportedOperationException("Not supported for unit test")
    }
}