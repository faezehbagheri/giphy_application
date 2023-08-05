package com.example.giphyapplication.ui.gifdetails.contract

import com.example.domain.model.Gif


sealed class GifDetailsViewState {
    object Loading : GifDetailsViewState()
    object Error : GifDetailsViewState()
    data class Result(val gif: Gif) : GifDetailsViewState()
}
