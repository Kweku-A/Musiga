package com.kweku.armah.ui.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kweku.armah.ui.R
import com.kweku.armah.ui.custom.GridItem
import com.kweku.armah.ui.custom.MotionSearchToolBar
import com.kweku.armah.ui.model.SessionUi

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

    val shuffledSearchList: () -> List<SessionUi> = {
        searchList.shuffled()
    }

    val error = viewModel.errorMessage

    val searchText by viewModel.searchedText.collectAsState()
    val searchTextProvider: () -> String = {
        searchText
    }

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
                title = stringResource(R.string.title_discover),
                motionProgressProvider = { progress.value },
                maxHeightOfToolBarProvider = paddingProvider,
                searchTextProvider = searchTextProvider,
                onSearchTextChanged = onSearchTextChanged
            )
        },
        containerColor = Color.Black,

        ) { paddingValues ->
        val padding1 = paddingValues
        Surface(
            modifier = Modifier
                .padding(top = padding)
                .fillMaxSize(), color = Color.Black
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
    indicatorRef: ConstrainedLayoutReference,
    linkedToRef: ConstrainedLayoutReference
) {
    Box(
        contentAlignment = Alignment.TopCenter,
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
        CircularProgressIndicator(modifier = Modifier.size(30.dp))
    }

}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen() {}
}