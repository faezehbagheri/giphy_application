package com.example.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.usecase.GetTrendingGifsUseCase
import com.example.domain.usecase.SearchOnGifsUseCase
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
    private val searchOnGifsUseCase: SearchOnGifsUseCase,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(GifsListViewState())

    val uiState = viewModelState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = viewModelState.value
    )

    init {
        getTrendingGifs()
    }

    fun search(searchTerms: String) {
        if (searchTerms.isNotEmpty()) {
            viewModelState.update { state ->
                state.copy(
                    searchTerms = searchTerms,
                    pagingData = searchOnGifsUseCase(searchTerms).cachedIn(viewModelScope)
                )
            }
        } else {
            getTrendingGifs()
        }
    }

    private fun getTrendingGifs() {
        viewModelState.update { state ->
            state.copy(
                pagingData = getTrendingGifsUseCase().cachedIn(viewModelScope)
            )
        }
    }
}
