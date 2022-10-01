package com.kweku.armah.database.dao.fake

import com.kweku.armah.database.dao.RemoteKeysDao
import com.kweku.armah.database.entity.keys.RemoteKeys

class FakeRemoteKeysDao : RemoteKeysDao {

    var itemList: MutableList<RemoteKeys> = mutableListOf()

    override suspend fun insertAll(remoteKeys: List<RemoteKeys>) {
        itemList.addAll(remoteKeys)
    }

    override suspend fun remoteKeysEntitiesId(id: Long): RemoteKeys {
        return itemList.first {
            it.id == id
        }
    }

    override suspend fun remoteKeysFirstItem(): RemoteKeys {

        return itemList.first()
    }

    override suspend fun remoteKeysLastItem(): RemoteKeys {
        return itemList.last()
    }

    override suspend fun deleteRemoteKeys() {
        itemList.clear()
    }
}