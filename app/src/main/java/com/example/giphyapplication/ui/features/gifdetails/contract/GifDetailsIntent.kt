package com.example.giphyapplication.ui.features.gifdetails.contract

sealed class GifDetailsIntent{
    data class GetGifDetails(val id: String) : GifDetailsIntent()
}
