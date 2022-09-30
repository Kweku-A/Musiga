package com.kweku.armah.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedUseCases: FeedUseCases) : ViewModel() {

    var pagingDataSession by mutableStateOf<Flow<PagingData<SessionUi>>>(flowOf())
        private set

    private val _searchDataSession = MutableStateFlow<List<SessionUi>>(emptyList())
    val searchDataSession: StateFlow<List<SessionUi>> = _searchDataSession

    var errorMessage by mutableStateOf("")
        private set

    init {
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

    fun onSearchText(search: String) {
        searchedText.value = search
        viewModelScope.coroutineContext.cancelChildren()
        viewModelScope.launch {
            when (val response = feedUseCases.searchFeedUseCase()) {
                is ApiResult.ApiSuccess -> {
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
                }

                is ApiResult.ApiError -> {
                    errorMessage = response.type.message
                }
            }
        }
    }
}