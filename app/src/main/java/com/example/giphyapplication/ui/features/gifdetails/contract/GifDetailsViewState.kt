package com.example.giphyapplication.ui.features.gifdetails.contract

import com.example.giphyapplication.domain.model.Gif

sealed class GifDetailsViewState {
    object Loading : GifDetailsViewState()
    object Error : GifDetailsViewState()
    data class Result(val gif: Gif) : GifDetailsViewState()
}
