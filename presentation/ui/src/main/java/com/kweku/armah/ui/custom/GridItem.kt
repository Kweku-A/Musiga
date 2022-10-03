package com.kweku.armah.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.kweku.armah.ui.R
import com.kweku.armah.ui.model.SessionUi

@Composable
fun GridItem(session: SessionUi, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(size = 10.dp),
        color = Color.Transparent,
        modifier = modifier
    ) {

        val contentDescription: () -> String = {
            session.name
        }
        val context = LocalContext.current

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(session.artworkUrl)
                .fallback(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .scale(Scale.FIT)
                .build(),
        )

        val brush = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                Color.Black.copy(alpha = 0.2f),
                Color.Black.copy(alpha = 0.4f)
            )
        )

        Box {
            Image(
                painter = painter,
                contentDescription = contentDescription(),
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = brush)
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

@Composable
internal fun GridItemPlaceholder(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(size = 10.dp),
        modifier = modifier,
        color = Color.Transparent
    ) {

        Image(
            painter = painterResource(R.drawable.ic_placeholder),
            contentDescription = "placeholder",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}