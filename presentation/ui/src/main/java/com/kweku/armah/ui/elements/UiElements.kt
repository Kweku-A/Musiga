package com.kweku.armah.ui.elements

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.model.fake.fakeSession3
import com.kweku.armah.ui.R
import kotlin.math.min


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleToolBar(scrollState: LazyGridState, content: @Composable () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val scrollOffset: Float = min(
        1f,
        1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex)
    )


//    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            LargeTopAppBar(
//                title = { Text(text = "Discover", textAlign = TextAlign.Center) },
//                navigationIcon = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(imageVector = Icons.Default.Menu, contentDescription = "")
//                    }
//                },
//                scrollBehavior = scrollBehavior
//            )
//    SearchCollapsibleTopBar(title = "Discover", scrollOffset = scrollOffset, content = content)
//        }
//    ) {
//        content(it)
//    }

    MotionCollapsibleToolbar(scrollOffset = scrollOffset, content = content)
}

private val MIN = 72.dp
private val MAX = 128.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchCollapsibleTopBar(
    title: String,
    scrollOffset: Float,
    content: @Composable () -> Unit
) {
    val imageSize by animateDpAsState(targetValue = max(72.dp, 128.dp * scrollOffset))
    Log.d("SearchCollapsibleTopBar", "SearchCollapsibleTopBar: imageSize $imageSize")
    Column {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .height(imageSize),
        ) {
            Column(verticalArrangement = Arrangement.Bottom) {

                var searchText by remember {
                    mutableStateOf("")
                }
                AnimatedContent(targetState = imageSize, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = title,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = if (imageSize == MIN) TextAlign.Center else TextAlign.Left,
                    )
                }
                AnimatedVisibility(
                    visible = imageSize != MIN,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    SearchBar(searchText = searchText, placeholderText = "Search") { text ->
                        searchText = text
                    }
                }
            }
        }

        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchText: String,
    placeholderText: String = "Search",
    onSearchTextChanged: (String) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = { Text(text = placeholderText) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = LocalContentColor.current.copy(alpha = 0.4f)
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "search field"
            )
        },
        trailingIcon = {},
        maxLines = 1,
        singleLine = true,
    )
}


@Composable
fun GridItem(session: Session, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(size = 10.dp),
        modifier = modifier,
        color = Color.Transparent
    ) {
        Box() {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(session.currentTrack.artworkUrl)
                        .crossfade(enable = true)
                        .build()
                ),
                contentDescription = session.name,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.4f))
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
                    text = session.genres.joinToString(", "),
                    color = Color.White, fontSize = 11.sp
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

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ListenerIconPreview() {
    MaterialTheme {
        GridItem(
            session = fakeSession3, modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
    }
}