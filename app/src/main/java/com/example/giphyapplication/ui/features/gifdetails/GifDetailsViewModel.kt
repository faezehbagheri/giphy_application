package com.example.giphyapplication.ui.features.gifdetails

import androidx.lifecycle.SavedStateHandle
import com.example.giphyapplication.common.result.GetResult
import com.example.giphyapplication.domain.usecase.GetGifByIdUseCase
import com.example.giphyapplication.framework.BaseViewModel
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsIntent
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class GifDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    initialState: GifDetailsUiState,
    private val getGifByIdUseCase: GetGifByIdUseCase,
) : BaseViewModel<GifDetailsUiState, GifDetailsUiState.PartialState, GifDetailsIntent>(
    savedStateHandle,
    initialState
) {

    override fun mapIntents(intent: GifDetailsIntent): Flow<GifDetailsUiState.PartialState> =
        when (intent) {
            is GifDetailsIntent.GetGifDetails -> getGifDetails(intent.id)
        }

    override fun reduceUiState(
        previousState: GifDetailsUiState,
        partialState: GifDetailsUiState.PartialState
    ): GifDetailsUiState = when (partialState) {
        is GifDetailsUiState.PartialState.OnViewStateChanged -> {
            previousState.copy(
                viewState = partialState.viewState
            )
        }
        is GifDetailsUiState.PartialState.OnGifReceived -> {
            previousState.copy(
                gif = partialState.gif,
                viewState = GifDetailsViewState.Success
            )
        }
    }

    private fun getGifDetails(id: String): Flow<GifDetailsUiState.PartialState> = flow {
        getGifByIdUseCase(id).collect { result ->
            when (result) {
                is GetResult.Loading -> {
                    emit(GifDetailsUiState.PartialState.OnViewStateChanged(GifDetailsViewState.Loading))
                }
                is GetResult.Error -> {
                    emit(GifDetailsUiState.PartialState.OnViewStateChanged(GifDetailsViewState.Error))
                }
                is GetResult.Success -> {
                    result.data?.let {
                        emit(GifDetailsUiState.PartialState.OnGifReceived(it))
                    }
                }
            }
        }
    }
}