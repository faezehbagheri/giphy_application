package com.example.giphyapplication.ui.features.gifslist

import androidx.lifecycle.SavedStateHandle
import com.example.giphyapplication.domain.usecase.GetTrendingGifsUseCase
import com.example.giphyapplication.framework.BaseViewModel
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
) : BaseViewModel<GifsListUiState, GifsListUiState.PartialState, GifsListIntent>(
    savedStateHandle,
    initialState
) {

    init {
        getNews()
    }

    override fun mapIntents(intent: GifsListIntent): Flow<GifsListUiState.PartialState> =
        when (intent) {
            is GifsListIntent.OnReceivedGiftsList -> flow {
                emit(GifsListUiState.PartialState.OnReceivedGiftsList(intent.gifs))
            }
        }

    override fun reduceUiState(
        previousState: GifsListUiState,
        partialState: GifsListUiState.PartialState
    ): GifsListUiState = when (partialState) {
        is GifsListUiState.PartialState.OnReceivedGiftsList -> {
            previousState.copy(
                pagingGifs = partialState.gifs,
            )
        }
    }

    private fun getNews() = safeLaunch {
        acceptIntent(
            GifsListIntent.OnReceivedGiftsList(
                getTrendingGifsUseCase.invoke()
            )
        )
    }
}