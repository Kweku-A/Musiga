package com.kweku.armah.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kweku.armah.ui.custom.GridItem
import com.kweku.armah.ui.custom.MotionSearchToolBar
import com.kweku.armah.ui.model.SessionUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel(), navigateBack: () -> Unit) {

    val feedPagingItems: LazyPagingItems<SessionUi> =
        viewModel.pagingDataSession.collectAsLazyPagingItems()

    val scrollState = rememberLazyGridState()

    val progress by animateFloatAsState(
        targetValue = if (remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value in 0..1) 0f else 1f,
        tween(500)
    )

    val padding by animateDpAsState(
        targetValue = if (remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value in 0..1) 216.dp else 90.dp,
        tween(500)
    )
    var isLoadingItems by remember {
        mutableStateOf(false)
    }

    val onLoadingItemsEvent: (LazyPagingItems<SessionUi>) -> Unit = {
        it.apply {
            isLoadingItems = when {
                loadState.mediator?.refresh is LoadState.Loading -> {
                    true
                }

                loadState.mediator?.append is LoadState.Loading -> {
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    val searchList by viewModel.searchDataSession.collectAsState(initial = emptyList())

    val searchText by viewModel.searchedText.collectAsState()

    var isSearching by remember {
        mutableStateOf(false)
    }

    val onSearchTextChanged: (String) -> Unit = {
        isSearching = it.isNotEmpty()
        viewModel.onSearchText(it)
    }

    Scaffold(
        topBar = {
            MotionSearchToolBar(
                title = "Discover",
                motionProgress = progress,
                maxHeightOfToolBar = padding,
                searchText = searchText,
                onSearchTextChanged = onSearchTextChanged
            )
        },
        containerColor = Color.Black,

        ) { paddingValues ->
        val padding1 = paddingValues
        Surface(
            modifier = Modifier
                .padding(top = padding)
                .fillMaxWidth(), color = Color.Black
        ) {
            ConstraintLayout {
                val (gridList, progressBar) = createRefs()
                if (!isSearching) {
                    LazyVerticalGrid(columns = GridCells.Fixed(2),
                        state = scrollState,
                        modifier = Modifier
                            .padding(bottom = 16.dp, start = 7.dp, end = 7.dp)
                            .fillMaxWidth()
                            .constrainAs(gridList) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(progressBar.top)
                            }) {
                        items(feedPagingItems.itemCount) {
                            val item = feedPagingItems[it]
                            if (item != null) {
                                GridItem(
                                    session = item,
                                    modifier = Modifier
                                        .padding(start = 8.dp, bottom = 16.dp, end = 8.dp)
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                )
                            }
                        }
                    }

                    onLoadingItemsEvent(feedPagingItems)

                    LoadingIndicator(
                        isLoadingItems = isLoadingItems,
                        indicatorRef = progressBar,
                        linkedToRef = gridList
                    )

                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = scrollState,
                        modifier = Modifier
                            .padding(bottom = 16.dp, start = 7.dp, end = 7.dp)
                            .fillMaxWidth()
                    ) {
                        items(items = searchList) {
                            GridItem(
                                session = it,
                                modifier = Modifier
                                    .padding(
                                        start = 8.dp, bottom = 16.dp, end = 8.dp
                                    )
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
private fun ConstraintLayoutScope.LoadingIndicator(
    isLoadingItems: Boolean,
    indicatorRef: ConstrainedLayoutReference,
    linkedToRef: ConstrainedLayoutReference
) {
    AnimatedVisibility(visible = isLoadingItems,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)),
        modifier = Modifier
            .fillMaxWidth()
            .constrainAs(indicatorRef) {
                top.linkTo(linkedToRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {
        Box(
            contentAlignment = Alignment.TopCenter, modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            CircularProgressIndicator(modifier = Modifier.size(30.dp))
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen() {}
}