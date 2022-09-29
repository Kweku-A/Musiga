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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.kweku.armah.domain.model.Session
import com.kweku.armah.ui.custom.MotionSearchToolBar
import com.kweku.armah.ui.elements.GridItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel(), navigateBack: () -> Unit) {

    val itemList: LazyPagingItems<Session> = viewModel.pagingDataSession.collectAsLazyPagingItems()
    val scrollState = rememberLazyGridState()

    val progress by animateFloatAsState(
        targetValue = if (remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value in 0..1) 0f else 1f,
        tween(500)
    )

    val padding by animateDpAsState(
        targetValue = if (remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value in 0..1) 216.dp else 90.dp,
        tween(500)
    )

    val searchText by viewModel.searchedText.collectAsState()

    val onSearchTextChanged: (String) -> Unit = {
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

        ) {
        val padding1 = it
        Surface(
            modifier = Modifier
                .padding(top = padding)
                .fillMaxSize(),
            color = Color.Black
        ) {
            ConstraintLayout {

                val (gridList, progress) = createRefs()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = scrollState,
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 7.dp, end = 7.dp)
                        .fillMaxWidth()
                        .constrainAs(gridList) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(progress.top)
                        }
                ) {
                    items(itemList.itemCount) {
                        val item = itemList[it]
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
                itemList.apply {
                    when {
                        loadState.mediator?.refresh is LoadState.Loading -> {
                            LoadingIndicator(progress,gridList)
                        }

                        loadState.mediator?.append is LoadState.Loading -> {
                            LoadingIndicator(progress,gridList)
                        }

                        loadState.mediator?.refresh is LoadState.Error -> {

                        }

                        loadState.mediator?.append is LoadState.Error -> {

                        }
                    }
                }


            }
        }
    }
}

@Composable
private fun ConstraintLayoutScope.LoadingIndicator(
    indicatorRef: ConstrainedLayoutReference,
    linkedToRef: ConstrainedLayoutReference
) {
    Box(contentAlignment = Alignment.TopCenter,modifier = Modifier.fillMaxWidth().height(80.dp).constrainAs(indicatorRef) {
        top.linkTo(linkedToRef.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
    }) {
        CircularProgressIndicator(modifier = Modifier.size(30.dp))
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen() {
    }
}