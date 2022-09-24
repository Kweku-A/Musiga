package com.kweku.armah.network.entity

import kotlinx.serialization.SerialName

import kotlinx.serialization.Serializable

@Serializable
data class FeedDto(
    @SerialName("data")
    val responseDataDto: DataDto
)