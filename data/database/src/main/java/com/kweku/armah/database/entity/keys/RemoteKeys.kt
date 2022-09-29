package com.kweku.armah.database.entity.keys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val previousKey: Int?,
    val nextKey: Int?
)