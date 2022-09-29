package com.kweku.armah.domain.model.fake

import com.kweku.armah.domain.model.CurrentTrack
import com.kweku.armah.domain.model.Session

val fakeSession1 = Session(
    id=1,
    currentTrack = CurrentTrack(artworkUrl = "a", title = "b"),
    genres = listOf("c", "d", "e"),
    listenerCount = 1,
    name = "f"
)

val fakeSession2 = Session(
    id=2,
    currentTrack = CurrentTrack(artworkUrl = "g", title = "h"),
    genres = listOf("i", "j", "k"),
    listenerCount = 2,
    name = "l"
)

val fakeSession3 = Session(
    id=3,
    currentTrack = CurrentTrack(artworkUrl = "https://i.scdn.co/image/05c1c3fa2e2cca7011c8c94751d7f21f4aff5b54", title = "Passage"),
    genres = listOf("Jazz", "Pop", "Electric"),
    listenerCount = 229,
    name = "jazz"
)

val fakeSessions = listOf(
    fakeSession1,
    fakeSession2
)