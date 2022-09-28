package com.kweku.armah.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kweku.armah.database.entity.keys.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun remoteKeysEntitiesId(id: Long): RemoteKeys?

    @Query("SELECT * FROM remote_keys ORDER BY id ASC LIMIT 1")
    suspend fun remoteKeysFirstItem(): RemoteKeys?

    @Query("SELECT * FROM remote_keys ORDER BY id DESC LIMIT 1")
    suspend fun remoteKeysLastItem(): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}