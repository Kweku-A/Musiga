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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kweku.armah.ui.R
import com.kweku.armah.ui.custom.GridItem
import com.kweku.armah.ui.custom.MotionSearchToolBar
import com.kweku.armah.ui.model.SessionUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel(), navigateBack: () -> Unit) {

    val feedPagingItems: LazyPagingItems<SessionUi> =
        viewModel.pagingDataSession.collectAsLazyPagingItems()

    val scrollState = rememberLazyGridState()

    val progress = animateFloatAsState(
        targetValue = if (remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value in 0..1) 0f else 1f,
        tween(500)
    )

    val padding by animateDpAsState(
        targetValue = if (remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value in 0..1) 216.dp else 90.dp,
        tween(500)
    )

    val paddingProvider: () -> Dp = {
        padding
    }

    val isLoadingItems by viewModel.isLoadingItems.collectAsState()

    val searchList by viewModel.searchDataSession.collectAsState(initial = emptyList())

    val searchText by viewModel.searchedText.collectAsState()

    val searchTextProvider: () -> String = {
        searchText
    }

    val isSearching by viewModel.isSearching.collectAsState()

    val onSearchTextChanged: (String) -> Unit = {
        viewModel.onSearchText(it)
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val retryFeed: () -> Unit = {
        viewModel.getFeed()
    }

    val onLoadingItemsEvent: (LazyPagingItems<SessionUi>) -> Unit = {
        viewModel.getState(it)
    }

    val errorMessage by viewModel.errorMessage.collectAsState()
    val retryText = stringResource(R.string.retry)
    val showErrorMessage: (CoroutineScope) -> Unit = { scope ->
        scope.launch {
            snackBarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Indefinite,
                actionLabel = retryText
            )
        }
    }
    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage.isNotEmpty()) {
            showErrorMessage(this)
        }
    }

    Scaffold(
        topBar = {
            MotionSearchToolBar(
                title = stringResource(R.string.title_discover),
                motionProgressProvider = { progress.value },
                isSearchingProvider = { isSearching },
                maxHeightOfToolBarProvider = paddingProvider,
                searchTextProvider = searchTextProvider,
                onSearchTextChanged = onSearchTextChanged
            )
        }, containerColor = Color.Black, snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) {
            Snackbar(
                Modifier,
                action = {
                    Button(
                        onClick = {
                            retryFeed()
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
    }

    ) { paddingValues ->
        val padding1 = paddingValues
        Surface(
            modifier = Modifier
                .padding(
                    top = padding, bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxSize(),
            color = Color.Black
        ) {
            ConstraintLayout {
                val (gridList, progressBar) = createRefs()

                if (searchText.isEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = scrollState,
                        modifier = Modifier
                            .padding(bottom = 16.dp, start = 7.dp, end = 7.dp)
                            .fillMaxSize()
                            .constrainAs(gridList) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(progressBar.top)
                            }
                    ) {
                        items(feedPagingItems.itemCount) {
                            val item = feedPagingItems[it]
                            if (item != null) {
                                GridItem(
                                    session = item,
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

                    onLoadingItemsEvent(feedPagingItems)

                    LoadingIndicator(
                        isLoadingItemsProvider = { isLoadingItems },
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

@Stable
@Composable
private fun ConstraintLayoutScope.LoadingIndicator(
    isLoadingItemsProvider: () -> Boolean,
    indicatorRef: ConstrainedLayoutReference,
    linkedToRef: ConstrainedLayoutReference
) {
    AnimatedVisibility(
        visible = isLoadingItemsProvider(),
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .constrainAs(indicatorRef) {
                top.linkTo(linkedToRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
    ) {
        Box(
            contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()
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