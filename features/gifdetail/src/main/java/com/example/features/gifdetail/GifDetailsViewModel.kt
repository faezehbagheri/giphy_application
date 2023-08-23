package com.example.features.gifdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetGifByIdUseCase
import com.example.features.gifdetail.contract.GifDetailsViewState
import com.example.libraries.common.result.GetResult
import com.example.libraries.navigation.DestinationArgs
import com.example.libraries.utils.coroutines.safeLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GifDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGifByIdUseCase: GetGifByIdUseCase,
) : ViewModel() {

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
        getGifByIdUseCase(gifId).collectLatest { result ->
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
