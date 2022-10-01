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
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedUseCases: FeedUseCases) : ViewModel() {

    var pagingDataSession by mutableStateOf<Flow<PagingData<SessionUi>>>(flowOf())
        private set

    private val _searchDataSession = MutableStateFlow<List<SessionUi>>(emptyList())
    val searchDataSession: StateFlow<List<SessionUi>> = _searchDataSession

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private var _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private var _isLoadingItems = MutableStateFlow(false)
    val isLoadingItems: StateFlow<Boolean> = _isLoadingItems

    init {
        getFeed()
    }

    fun getFeed() {
        // val pagingData = feedUseCases.getFeedUseCase().cachedIn(viewModelScope)

        pagingDataSession =
            feedUseCases.getFeedUseCase().cachedIn(viewModelScope).map { pagingDataSession ->
                pagingDataSession.map {
                    SessionUi(
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

    fun onSearchText(searchParam: String) {
        searchedText.value = searchParam
        viewModelScope.coroutineContext.cancelChildren()
        if (searchParam.isNotEmpty()) {
            _isSearching.value = true
            _errorMessage.value = ""
            viewModelScope.launch {
                when (val response = feedUseCases.searchFeedUseCase()) {
                    is ApiResult.ApiSuccess -> {
                        ensureActive()
                        val list = response.data.map {
                            SessionUi(
                                title = it.currentTrack.title,
                                artworkUrl = it.currentTrack.artworkUrl,
                                name = it.name,
                                genres = it.genres.joinToString(", "),
                                listenerCount = it.listenerCount
                            )
                        }
                        _searchDataSession.value = list.shuffled()
                        _isSearching.value = false
                    }

                    is ApiResult.ApiError -> {
                        ensureActive()
                        _errorMessage.value = response.type.message
                        _isSearching.value = false
                    }
                }
            }
        } else {
            _isSearching.value = false
            getFeed()
        }
    }

    fun getState(lazyPagingItems: LazyPagingItems<SessionUi>) {
        lazyPagingItems.apply {
            _isLoadingItems.value = when {
                loadState.mediator?.refresh is LoadState.Loading -> {
                    true
                }

                loadState.mediator?.append is LoadState.Loading -> {
                    true
                }

                loadState.mediator?.refresh is LoadState.Error -> {
                    _errorMessage.value = (loadState.mediator?.refresh as LoadState.Error).error.message.orEmpty()
                    false
                }

                loadState.mediator?.append is LoadState.Error -> {
                    _errorMessage.value = (loadState.mediator?.refresh as LoadState.Error).error.message.orEmpty()
                    false
                }

                else -> {
                    false
                }
            }
        }
    }
}