package com.example.giphyapplication.ui.features.gifdetails

import androidx.lifecycle.SavedStateHandle
import com.example.giphyapplication.framework.BaseViewModel
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsEvent
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsIntent
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class GifDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    initialState: GifDetailsUiState,
) : BaseViewModel<GifDetailsUiState, GifDetailsUiState.PartialState, GifDetailsEvent, GifDetailsIntent>(
    savedStateHandle,
    initialState
) {

    override fun mapIntents(intent: GifDetailsIntent): Flow<GifDetailsUiState.PartialState> =
        when (intent) {
            else -> flow{}
        }

    override fun reduceUiState(
        previousState: GifDetailsUiState,
        partialState: GifDetailsUiState.PartialState
    ): GifDetailsUiState = when (partialState) {
        is GifDetailsUiState.PartialState.SetGifDetails -> {
            previousState.copy(
                gif = partialState.gif
            )
        }
    }
}