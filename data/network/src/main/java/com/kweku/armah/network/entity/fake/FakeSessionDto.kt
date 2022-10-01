package com.kweku.armah.network.entity.fake

import com.kweku.armah.network.entity.CurrentTrackDto
import com.kweku.armah.network.entity.DataDto
import com.kweku.armah.network.entity.FeedDto
import com.kweku.armah.network.entity.SessionDto

val feedSessionDto1 = SessionDto(
    currentTrackDto = CurrentTrackDto(artworkUrl = "a", title = "b"),
    genres = listOf("c", "d", "e"),
    listenerCount = 50,
    name = "f"
)

val feedSessionDto2 = SessionDto(
    currentTrackDto = CurrentTrackDto(artworkUrl = "g", title = "h"),
    genres = listOf("i", "d", "e"),
    listenerCount = 5,
    name = "j"
)

val fakeFeedDto = FeedDto(
    responseDataDto = DataDto(
        sessionDtos = listOf(
            feedSessionDto1, feedSessionDto2
        )
    )
)