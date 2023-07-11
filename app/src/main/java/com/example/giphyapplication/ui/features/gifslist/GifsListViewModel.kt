package com.example.giphyapplication.ui.features.gifslist

import androidx.lifecycle.SavedStateHandle
import com.example.giphyapplication.framework.BaseViewModel
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListEvent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListIntent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GifsListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    initialState: GifsListUiState,
) : BaseViewModel<GifsListUiState, GifsListUiState.PartialState, GifsListEvent, GifsListIntent>(
    savedStateHandle,
    initialState
) {
    override fun mapIntents(intent: GifsListIntent): Flow<GifsListUiState.PartialState> {
        TODO("Not yet implemented")
    }

    override fun reduceUiState(
        previousState: GifsListUiState,
        partialState: GifsListUiState.PartialState
    ): GifsListUiState {
        TODO("Not yet implemented")
    }
}