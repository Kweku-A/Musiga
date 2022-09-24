package com.kweku.armah.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    @SerialName("current_track")
    val currentTrackDto: CurrentTrackDto,
    @SerialName("genres")
    val genres: List<String>,
    @SerialName("listener_count")
    val listenerCount: Int,
    @SerialName("name")
    val name: String
)