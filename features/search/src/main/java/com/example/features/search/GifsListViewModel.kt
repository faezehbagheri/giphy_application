package com.example.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetTrendingGifsUseCase
import com.example.features.search.contract.GifsListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GifsListViewModel @Inject constructor(
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(GifsListViewState())

    val uiState = viewModelState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = viewModelState.value
    )

    init {
        getGifsList()
    }

    private fun getGifsList() {
        viewModelState.update { state ->
            state.copy( pagingData = getTrendingGifsUseCase())
        }
    }
}
