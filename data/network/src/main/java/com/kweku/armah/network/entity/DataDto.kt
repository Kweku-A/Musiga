package com.kweku.armah.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataDto(
    @SerialName("sessions")
    val sessionDtos: List<SessionDto>
)