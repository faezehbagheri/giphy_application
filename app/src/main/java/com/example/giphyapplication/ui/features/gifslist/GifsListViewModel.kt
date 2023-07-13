package com.example.giphyapplication.ui.features.gifslist

import androidx.lifecycle.SavedStateHandle
import com.example.giphyapplication.common.result.GetResult
import com.example.giphyapplication.domain.usecase.GetTrendingGifsUseCase
import com.example.giphyapplication.framework.BaseViewModel
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListEvent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListIntent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class GifsListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    initialState: GifsListUiState,
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase,
) : BaseViewModel<GifsListUiState, GifsListUiState.PartialState, GifsListEvent, GifsListIntent>(
    savedStateHandle,
    initialState
) {

    init {
        acceptIntent(GifsListIntent.FetchGifsList)
    }

    override fun mapIntents(intent: GifsListIntent): Flow<GifsListUiState.PartialState> =
        when (intent) {
            is GifsListIntent.FetchGifsList -> fetchGifsList()
            is GifsListIntent.NavigateToGifDetails -> flow {
                publishEvent(GifsListEvent.NavigateToGifDetails(intent.id))
            }
        }

    override fun reduceUiState(
        previousState: GifsListUiState,
        partialState: GifsListUiState.PartialState
    ): GifsListUiState = when (partialState) {
        is GifsListUiState.PartialState.Error -> previousState.copy(
            isError = partialState.show,
            errorMessage = partialState.msg,
            isLoading = false,
            isEmpty = false
        )
        GifsListUiState.PartialState.Loading -> previousState.copy(
            isLoading = true, isError = false, isEmpty = false
        )
        GifsListUiState.PartialState.NetworkError -> previousState.copy(
            isLoading = false, isError = true, isEmpty = false
        )
        is GifsListUiState.PartialState.ReceivedGiftsList -> {
            previousState.copy(
                gifsList = partialState.gifs.toMutableList(),
                isLoading = false,
                isError = false,
                isEmpty = partialState.gifs.isEmpty()
            )
        }
    }

    private fun fetchGifsList(): Flow<GifsListUiState.PartialState> = flow {
        getTrendingGifsUseCase().collect { result ->
            when (result) {
                is GetResult.Success -> {
                    result.data?.let { data ->
                        emit(GifsListUiState.PartialState.ReceivedGiftsList(data))
                    }
                }
                is GetResult.Error -> emit(GifsListUiState.PartialState.NetworkError)
                is GetResult.Loading -> emit(GifsListUiState.PartialState.Loading)
            }
        }
    }
}