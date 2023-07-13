package com.example.giphyapplication.ui.features.gifdetails.contract

import com.example.giphyapplication.domain.model.Gif

sealed class GifDetailsIntent{
    data class SetGifDetails(val gif: Gif) : GifDetailsIntent()
    object OnNavigateUp : GifDetailsIntent()
}
