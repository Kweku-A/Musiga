package com.kweku.armah.domain.model

data class Session(
    val currentTrack: CurrentTrack,
    val genres: List<String>,
    val listenerCount: Int,
    val name: String
)