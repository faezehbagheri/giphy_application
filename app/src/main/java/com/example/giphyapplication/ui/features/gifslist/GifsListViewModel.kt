package com.example.giphyapplication.ui.features.gifslist

import androidx.lifecycle.viewModelScope
import com.example.giphyapplication.domain.usecase.GetTrendingGifsUseCase
import com.example.giphyapplication.framework.BaseViewModel
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GifsListViewModel @Inject constructor(
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase,
) : BaseViewModel() {

    private val viewModelState = MutableStateFlow(GifsListViewState())
    val uiState = viewModelState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = viewModelState.value
    )

    init {
        getGifsList()
    }

    private fun getGifsList() = safeLaunch {
        viewModelState.update { state ->
            state.copy( pagingData = getTrendingGifsUseCase())
        }
    }
}