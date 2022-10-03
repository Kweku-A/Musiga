package com.kweku.armah.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class SessionUi(
    val id: Long,
    val artworkUrl: String,
    val title: String,
    val genres: String,
    val listenerCount: Int,
    val name: String
)
