package com.kweku.armah.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedDto(
    @SerialName("data")
    val responseDataDto: DataDto
)

@Serializable
data class DataDto(
    @SerialName("sessions")
    val sessionDtos: List<SessionDto>
)

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

@Serializable
data class CurrentTrackDto(
    @SerialName("artwork_url")
    val artworkUrl: String,
    @SerialName("title")
    val title: String
)
