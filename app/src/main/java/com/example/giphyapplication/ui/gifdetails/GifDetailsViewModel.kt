package com.example.giphyapplication.ui.gifdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetGifByIdUseCase
import com.example.giphyapplication.framework.BaseViewModel
import com.example.giphyapplication.ui.gifdetails.contract.GifDetailsViewState
import com.example.libraries.navigation.DestinationArgs
import com.example.common.result.GetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GifDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGifByIdUseCase: GetGifByIdUseCase,
) : BaseViewModel() {

    private val gifId: String = savedStateHandle.get<String>(DestinationArgs.GIF_ID) ?: ""

    private val viewModelState = MutableStateFlow<GifDetailsViewState>(
        GifDetailsViewState.Loading
    )

    val uiState = viewModelState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = viewModelState.value
    )

    init {
        getGifDetails()
    }

    private fun getGifDetails() = safeLaunch {
        getGifByIdUseCase(gifId).collect { result ->
            val state = when (result) {
                is GetResult.Error -> GifDetailsViewState.Error
                is GetResult.Loading -> GifDetailsViewState.Loading
                is GetResult.Success -> GifDetailsViewState.Result(result.data)
            }
            viewModelState.emit(state)
        }
    }

    fun retry() {
        getGifDetails()
    }
}