package com.kweku.armah.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kweku.armah.domain.model.Session
import com.kweku.armah.ui.R

@Composable
fun ListenerIcon(session: Session, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = Color.White.copy(alpha = 0.7f)
        ) {
            val image: Painter = painterResource(id = R.drawable.ic_listener)
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = image,
                    contentDescription = "listener icon",
                    modifier = modifier
                        .size(width = 16.dp, 13.dp)
                        .padding(end = 2.dp)
                )
                Text(
                    text = session.listenerCount.toString(),
                    fontSize = 12.sp,
                )
            }
        }
    }
}