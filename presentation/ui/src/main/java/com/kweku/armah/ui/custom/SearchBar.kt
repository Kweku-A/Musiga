package com.kweku.armah.ui.custom

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kweku.armah.ui.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun SearchBar(
    searchTextProvider: () -> String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Search",
    isSearchingProvider: () -> Boolean,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val showLeadingIcon: @Composable () -> Unit = {
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
    }

    val textFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        containerColor = Color.DarkGray, textColor = Color.White,
        cursorColor = Color.White
    )

    OutlinedTextField(
        modifier = modifier,
        value = searchTextProvider(),
        onValueChange = onSearchTextChanged,
        shape = RoundedCornerShape(20.dp),
        placeholder = {
            Text(
                text = placeholderText,
                color = Color.Gray,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        },
        colors = textFieldColors,
        leadingIcon = showLeadingIcon,
        maxLines = 1,
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
        })
    )
}