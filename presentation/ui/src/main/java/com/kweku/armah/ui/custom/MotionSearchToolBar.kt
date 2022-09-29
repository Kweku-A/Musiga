package com.kweku.armah.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.kweku.armah.ui.R

@OptIn(ExperimentalMotionApi::class)
@Composable
internal fun MotionSearchToolBar(
    title: String,
    motionProgress: Float,
    maxHeightOfToolBar: Dp,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val innerPadding = 16.dp
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.collapse_toolbar).readBytes().decodeToString()
    }

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = motionProgress,
        modifier = modifier
            .fillMaxWidth()
            .height(maxHeightOfToolBar)
            .background(color = Color.Black)
        // debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxHeightOfToolBar - innerPadding)
                .background(color = Color.White.copy(alpha = 0.15f))
                .layoutId("box")
        )
        Text(
            text = title,
            modifier = Modifier
                .layoutId("title"),
            textAlign = TextAlign.Center,
            color = Color.White
        )
        SearchBar(searchText = searchText, onSearchTextChanged = onSearchTextChanged, modifier = Modifier.layoutId("search"))
    }
}