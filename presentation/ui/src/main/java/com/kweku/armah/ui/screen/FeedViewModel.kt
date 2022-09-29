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
import com.kweku.armah.ui.model.SessionUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedUseCases: FeedUseCases) : ViewModel() {

    var pagingDataSession by mutableStateOf<Flow<PagingData<SessionUi>>>(flowOf())
        private set

    var searchDataSession: Flow<List<SessionUi>> = (emptyFlow())
        private set

    init {
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

    fun onSearchText(search: String) {
        searchedText.value = search
        viewModelScope.launch {
            searchDataSession = flowOf(
                feedUseCases.searchFeedUseCase(search).value.shuffled().map {
                    SessionUi(
                        id = it.id,
                        title = it.currentTrack.title,
                        artworkUrl = it.currentTrack.artworkUrl,
                        name = it.name,
                        genres = it.genres.joinToString(", "),
                        listenerCount = it.listenerCount
                    )
                }
            )
        }
    }
}