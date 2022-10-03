package com.kweku.armah.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.kweku.armah.ui.R

@Composable
internal fun SearchBar(
    searchTextProvider: () -> String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String,
    isSearchingProvider: () -> Boolean,
) {
    Box(modifier = modifier.clip(shape = RoundedCornerShape(20.dp))) {
        Row(
            modifier = Modifier
                .background(color = Color.DarkGray)
                .padding(10.dp)
        ) {
            when (isSearchingProvider()) {
                true -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(25.dp)
                    )
                }

                false -> {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_field),
                        tint = Color.Gray
                    )
                }
            }

            CustomTextField(
                placeholderText = placeholderText,
                searchTextProvider = searchTextProvider,
                onSearchTextChanged = onSearchTextChanged
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CustomTextField(
    placeholderText: String,
    searchTextProvider: () -> String,
    onSearchTextChanged: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxWidth()) {
        if (searchTextProvider().isEmpty())
            Text(
                text = placeholderText,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
            )

        BasicTextField(
            value = searchTextProvider(),
            onValueChange = onSearchTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            cursorBrush = Brush.verticalGradient(
                colors = listOf(
                    Color.White,
                    Color.White,
                )
            ),
            maxLines = 1,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            })
        )
    }
}