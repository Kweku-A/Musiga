package com.kweku.armah.ui.screen

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kweku.armah.domain.model.Session
import com.kweku.armah.ui.elements.CollapsibleToolBar
import com.kweku.armah.ui.elements.GridItem

@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel(), navigateBack: () -> Unit) {

    val itemList: LazyPagingItems<Session> = viewModel.pagingDataSession.collectAsLazyPagingItems()
    val scrollState = rememberLazyGridState()

    CollapsibleToolBar(scrollState) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            color = Color.Black
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 7.dp)
                    .fillMaxSize(),
                state = scrollState
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
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen() {
    }
}