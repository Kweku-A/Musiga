package com.kweku.armah.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kweku.armah.ui.R
import com.kweku.armah.ui.custom.GridItem
import com.kweku.armah.ui.custom.MotionToolBarContent
import com.kweku.armah.ui.model.SessionUi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMotionApi::class
)
@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel(), navigateBack: () -> Unit) {

    val feedPagingItems: LazyPagingItems<SessionUi> =
        viewModel.pagingDataSession.collectAsLazyPagingItems()

    val feedPagingItemsCount: () -> Int = {
        feedPagingItems.itemCount
    }

    val gridScrollState = rememberLazyGridState()

    val progress = animateFloatAsState(
        targetValue = if (remember { derivedStateOf { gridScrollState.firstVisibleItemIndex } }.value in 0..1) 0f else 1f,
        tween(500)
    )

    val toolBarHeight by animateDpAsState(
        targetValue = if (remember { derivedStateOf { gridScrollState.firstVisibleItemIndex } }.value in 0..1) 200.dp else 90.dp,
        tween(500)
    )

    val isLoadingNextItems by viewModel.isLoadingNextItems.collectAsState()

    val searchedFeedList by viewModel.searchDataSession.collectAsState(initial = emptyList())

    val searchFeedText by viewModel.searchedText.collectAsState()

    val searchFeedTextProvider: () -> String = {
        searchFeedText
    }

    val isSearchingFeed by viewModel.isSearchingFeed.collectAsState()

    val onSearchFeedTextChanged: (String) -> Unit = {
        viewModel.onSearchFeedText(it)
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val retryFetchingFeed: () -> Unit = {
        viewModel.retryNetworkCall(searchFeedText)
    }

    val checkForNextItemsToLoad: (LazyPagingItems<SessionUi>) -> Unit = {
        viewModel.checkIfLoadingNextPage(it)
    }

    val errorMessage by viewModel.errorMessage.collectAsState()
    val retryText = stringResource(R.string.retry)

    val scope = rememberCoroutineScope()
    val showErrorMessage: () -> Unit = {
        scope.launch {
            snackBarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Indefinite,
                actionLabel = retryText
            )
        }
    }

    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.collapse_toolbar).readBytes().decodeToString()
    }

    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage.isNotEmpty()) {
            showErrorMessage()
        }
    }

    Scaffold(
        topBar = {
            MotionLayout(
                motionScene = MotionScene(content = motionScene),
                progress = progress.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.onSurface)
            ) {
                MotionToolBarContent(
                    heading = stringResource(R.string.title_discover),
                    isSearchingFeedProvider = { isSearchingFeed },
                    searchFeedTextProvider = searchFeedTextProvider,
                    onSearchFeedTextChanged = onSearchFeedTextChanged,
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.onSurface,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(
                    Modifier,
                    action = {
                        Button(
                            onClick = {
                                retryFetchingFeed()
                                it.dismiss()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(text = it.visuals.actionLabel.orEmpty())
                        }
                    },
                    dismissAction = {
                        IconButton(onClick = { it.dismiss() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.dismiss_snackbar_description),
                                tint = Color.Gray
                            )
                        }
                    },
                    actionOnNewLine = false,
                ) {
                    Text(it.visuals.message)
                }
            }
        },

        ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(
                    top = toolBarHeight, bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.onSurface
        ) {

            if (searchFeedText.isEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridScrollState,
                    contentPadding = PaddingValues(
                        top = 16.dp, start = 16.dp, end = 16.dp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    items(count = feedPagingItemsCount()) {
                        val sessionUi = feedPagingItems[it]
                        if (sessionUi != null) {
                            GridItem(
                                session = sessionUi,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )
                        }
                    }
                    item(span = { GridItemSpan(2) }) {
                        LoadingIndicator(
                            isLoadingItemsProvider = { isLoadingNextItems },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )
                    }
                }

                checkForNextItemsToLoad(feedPagingItems)
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridScrollState,
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 7.dp, end = 7.dp)
                        .fillMaxWidth()
                ) {
                    items(items = searchedFeedList) {
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

@Stable
@Composable
private fun LoadingIndicator(
    isLoadingItemsProvider: () -> Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isLoadingItemsProvider(),
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp)
                    .align(alignment = Alignment.TopCenter)
            )
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen() {}
}