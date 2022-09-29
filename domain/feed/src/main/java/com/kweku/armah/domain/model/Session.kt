package com.kweku.armah.domain.model

data class Session(
    val id:Long,
    val currentTrack: CurrentTrack,
    val genres: List<String>,
    val listenerCount: Int,
    val name: String
)