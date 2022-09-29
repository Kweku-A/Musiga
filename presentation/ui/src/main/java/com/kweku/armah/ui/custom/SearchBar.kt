package com.kweku.armah.ui.custom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun SearchBar(
    searchTextProvider: () -> String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderTextProvider: () -> String = { "Search" },
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        value = searchTextProvider(),
        onValueChange = onSearchTextChanged,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = placeholderTextProvider(),
                color = Color.Gray,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color.DarkGray, textColor = Color.White,
            cursorColor = Color.White
        ),
        leadingIcon = {
            val descriptionProvider: () -> String = { "search field" }

            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = descriptionProvider(),
                tint = Color.Gray
            )
        },
        maxLines = 1,
        singleLine = true,
    )
}