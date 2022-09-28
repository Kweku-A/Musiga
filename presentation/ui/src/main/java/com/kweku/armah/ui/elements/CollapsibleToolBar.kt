package com.kweku.armah.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.kweku.armah.ui.R

@OptIn(ExperimentalMotionApi::class, ExperimentalMaterial3Api::class)
@Composable
inline fun MotionCollapsibleToolbar(scrollOffset: Float, crossinline content: @Composable () -> Unit) {
    ConstraintLayout (modifier = Modifier.fillMaxSize()){
        val context = LocalContext.current
        val motionScene = remember {
            context.resources.openRawResource(R.raw.collapse_toolbar).readBytes().decodeToString()
        }

        var progress by remember {
            mutableStateOf(0f)
        }

        Surface {
            MotionLayout(
                motionScene = MotionScene(content = motionScene),
                progress = progress,
                modifier = Modifier.fillMaxSize()
                //debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(color = Color.Cyan)
                        .layoutId("box")
                )
                Text(
                    text = "Discover", modifier = Modifier
                        .layoutId("title"),
                    textAlign = TextAlign.Center
                )
                TextField(value = "", onValueChange = {}, Modifier.layoutId("search"))

                Column(modifier = Modifier.fillMaxWidth().layoutId("content")) {
                    Slider(
                        value = progress,
                        onValueChange = {
                            progress = it
                        },
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .layoutId("slider")
                    )

                    content()
                }


            }

        }
    }
}