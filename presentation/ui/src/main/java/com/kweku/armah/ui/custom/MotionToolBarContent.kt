package com.kweku.armah.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ExperimentalMotionApi
import com.kweku.armah.ui.R

@Composable
internal fun MotionToolBarContent(
    heading: String,
    isSearchingProvider:()->  Boolean,
    searchTextProvider: () -> String,
    onSearchTextChanged: (String) -> Unit,
) {

    val boxId = stringResource(R.string.box)
    val titleId = stringResource(R.string.title)
    val searchId = stringResource(R.string.search)

    val spacerModifier = Modifier
        .fillMaxWidth()
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
        searchTextProvider = searchTextProvider,
        onSearchTextChanged = onSearchTextChanged,
        isSearchingProvider = isSearchingProvider,
        modifier = searchModifier
    )
}