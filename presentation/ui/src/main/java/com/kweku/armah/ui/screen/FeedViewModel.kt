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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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

        viewModelScope.launch {
            feedUseCases.deleteLocalSearchFeedUsesCase()
        }
    }

    var searchedText = MutableStateFlow("")
        private set

    fun onSearchText(search: String) {
        searchedText.value = search
        viewModelScope.launch {
            searchDataSession =
                feedUseCases.searchFeedUseCase(search).map { list ->
                    list.map {
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
    }
}