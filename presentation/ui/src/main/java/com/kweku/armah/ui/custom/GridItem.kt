package com.kweku.armah.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kweku.armah.ui.model.SessionUi

@Composable
internal fun GridItem(session: SessionUi, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(size = 10.dp),
        modifier = modifier,
        color = Color.Transparent
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(session .artworkUrl)
                        .crossfade(enable = true)
                        .build()
                ),
                contentDescription = session.name,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.2f),
                                Color.Black.copy(alpha = 0.4f)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 16.dp)
                    .align(alignment = Alignment.BottomStart)
            ) {
                Text(
                    text = session.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = session.genres,
                    color = Color.White,
                    fontSize = 11.sp,
                    lineHeight = 11.sp
                )
            }

            ListenerIcon(
                session = session,
                modifier = Modifier
                    .width(30.dp)
                    .height(12.dp)
                    .align(Alignment.TopStart)
            )
        }
    }
}