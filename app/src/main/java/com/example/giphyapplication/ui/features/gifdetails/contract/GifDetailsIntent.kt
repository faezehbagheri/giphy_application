package com.example.giphyapplication.ui.features.gifdetails.contract

sealed class GifDetailsIntent{
    object FetchGifsList : GifDetailsIntent()
}
