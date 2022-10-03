package com.kweku.armah.database.entity.feed.fake

import com.kweku.armah.database.entity.feed.CurrentTrackEntity
import com.kweku.armah.database.entity.feed.FeedSessionEntity

val feedSessionEntity1 = FeedSessionEntity(
    id = 1,
    currentTrackEntity = CurrentTrackEntity(artworkUrl = "a", title = "b"),
    genres = listOf("c", "d", "e"),
    listenerCount = 50,
    name = "f"
)