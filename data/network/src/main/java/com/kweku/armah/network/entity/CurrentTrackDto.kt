package com.kweku.armah.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentTrackDto(
    @SerialName("artwork_url")
    val artworkUrl: String,
    @SerialName("title")
    val title: String
)