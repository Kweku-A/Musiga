package com.kweku.armah.database.entity.feed

val feedSessionEntity1 = FeedSessionEntity(
    id = 1,
    currentTrackEntity = CurrentTrackEntity(artworkUrl = "a", title = "b"),
    genres = listOf("c", "d", "e"),
    listenerCount = 50,
    name = "f"
)

val feedSessionEntity2 = FeedSessionEntity(
    id = 2,
    currentTrackEntity = CurrentTrackEntity(artworkUrl = "g", title = "h"),
    genres = listOf("i", "d", "e"),
    listenerCount = 5,
    name = "j"
)

val fakeSessionEntities = listOf(
    feedSessionEntity1, feedSessionEntity2
)