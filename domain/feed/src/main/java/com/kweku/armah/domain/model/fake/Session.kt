package com.kweku.armah.domain.model.fake

import com.kweku.armah.domain.model.CurrentTrack
import com.kweku.armah.domain.model.Session

val fakeSession1 = Session(
    currentTrack = CurrentTrack(artworkUrl = "a", title = "b"),
    genres = listOf("c", "d", "e"),
    listenerCount = 1,
    name = "f"
)

val fakeSession2 = Session(
    currentTrack = CurrentTrack(artworkUrl = "g", title = "h"),
    genres = listOf("i", "j", "k"),
    listenerCount = 2,
    name = "l"
)

val fakeSessions = listOf(
    fakeSession1,
    fakeSession2
)