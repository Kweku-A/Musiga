package com.kweku.armah.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kweku.armah.domain.model.Session
import com.kweku.armah.domain.usecase.FeedUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedUseCases: FeedUseCases) : ViewModel() {

    var pagingDataSession by mutableStateOf<Flow<PagingData<Session>>>(flowOf())
        private set

    init {
        pagingDataSession = feedUseCases.getFeedUseCase().cachedIn(viewModelScope)
    }

    var searchedText = MutableStateFlow("")
        private set

    fun onSearchText(search: String) {
        searchedText.value = search
    }
}