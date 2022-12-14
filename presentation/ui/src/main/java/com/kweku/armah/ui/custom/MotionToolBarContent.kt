package com.kweku.armah.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.kweku.armah.ui.R

@Composable
internal fun MotionToolBarContent(
    heading: String,
    placeholderText: String,
    isSearchingFeedProvider: () -> Boolean,
    searchFeedTextProvider: () -> String,
    onSearchFeedTextChanged: (String) -> Unit,
) {
    val boxId = stringResource(R.string.box)
    val titleId = stringResource(R.string.title)
    val searchId = stringResource(R.string.search)

    val spacerModifier = Modifier
        .fillMaxSize()
        .background(color = Color.White.copy(alpha = 0.12f))
        .layoutId(boxId)

    val titleModifier = Modifier
        .layoutId(titleId)

    val searchModifier = Modifier.layoutId(searchId)

    Spacer(
        modifier = spacerModifier
    )
    Text(
        text = heading,
        modifier = titleModifier,
        textAlign = TextAlign.Center,
        color = Color.White
    )
    SearchBar(
        placeholderText = placeholderText,
        searchTextProvider = searchFeedTextProvider,
        onSearchTextChanged = onSearchFeedTextChanged,
        isSearchingProvider = isSearchingFeedProvider,
        modifier = searchModifier
    )
}