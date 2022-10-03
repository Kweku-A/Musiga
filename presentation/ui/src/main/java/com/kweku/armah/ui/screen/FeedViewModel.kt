package com.kweku.armah.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.kweku.armah.domain.usecase.FeedUseCases
import com.kweku.armah.networkresult.ApiResult
import com.kweku.armah.ui.model.SessionUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedUseCases: FeedUseCases) : ViewModel() {

    var pagingDataSession by mutableStateOf<Flow<PagingData<SessionUi>>>(flowOf())
        private set

    private val _searchDataSession = MutableStateFlow<List<SessionUi>>(emptyList())
    val searchDataSession: StateFlow<List<SessionUi>> = _searchDataSession

    private val _isSearchingFeed = MutableStateFlow(false)
    val isSearchingFeed: StateFlow<Boolean> = _isSearchingFeed

    private var _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private var _isLoadingNextItems = MutableStateFlow(false)
    val isLoadingNextItems: StateFlow<Boolean> = _isLoadingNextItems

    init {
        getFeed()
    }

    fun getFeed() {
        pagingDataSession =
            feedUseCases.getFeedUseCase().cachedIn(viewModelScope).map { pagingDataSession ->
                pagingDataSession.map {
                    SessionUi(
                        id = it.id,
                        title = it.currentTrack.title,
                        artworkUrl = it.currentTrack.artworkUrl,
                        name = it.name,
                        genres = it.genres.joinToString(", "),
                        listenerCount = it.listenerCount
                    )
                }
            }
    }

    var searchedText = MutableStateFlow("")
        private set

    fun onSearchFeedText(searchParam: String) {
        searchedText.value = searchParam
        viewModelScope.coroutineContext.cancelChildren()
        if (searchParam.isNotEmpty()) {
            _errorMessage.value = ""
            viewModelScope.launch {
                _isSearchingFeed.value = true
                val response = feedUseCases.searchFeedUseCase()
                _isSearchingFeed.value = false
                if (isActive) {
                    when (response) {
                        is ApiResult.ApiSuccess -> {
                            val list = response.data.map {
                                SessionUi(
                                    id = it.id,
                                    title = it.currentTrack.title,
                                    artworkUrl = it.currentTrack.artworkUrl,
                                    name = it.name,
                                    genres = it.genres.joinToString(", "),
                                    listenerCount = it.listenerCount
                                )
                            }
                            _searchDataSession.value = list.shuffled()
                        }

                        is ApiResult.ApiError -> {
                            _errorMessage.value = response.type.message
                        }
                    }
                }
            }
        } else {
            _isSearchingFeed.value = false
            getFeed()
        }
    }

    fun checkIfLoadingNextPage(lazyPagingItems: LazyPagingItems<SessionUi>) {
        lazyPagingItems.apply {
            _isLoadingNextItems.value = when {
                loadState.mediator?.refresh is LoadState.Loading -> {
                    true
                }

                loadState.mediator?.append is LoadState.Loading -> {
                    true
                }

                loadState.mediator?.refresh is LoadState.Error -> {
                    _errorMessage.value =
                        (loadState.mediator?.refresh as LoadState.Error).error.message.orEmpty()
                    false
                }

                loadState.mediator?.append is LoadState.Error -> {
                    _errorMessage.value =
                        (loadState.mediator?.refresh as LoadState.Error).error.message.orEmpty()
                    false
                }

                else -> {
                    false
                }
            }
        }
    }
}