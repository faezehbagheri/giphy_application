package com.example.features.gifdetail.contract

import com.example.domain.model.GifDetail

sealed class GifDetailsViewState {
    data object Loading : GifDetailsViewState()
    data object Error : GifDetailsViewState()
    data class Result(val gif: GifDetail) : GifDetailsViewState()
}
