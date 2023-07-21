package com.example.giphyapplication.ui.features.gifdetails

sealed class GifDetailsViewState {
    object Loading : GifDetailsViewState()
    object Error : GifDetailsViewState()
    object Success : GifDetailsViewState()
}
