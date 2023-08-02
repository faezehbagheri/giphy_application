package com.example.giphyapplication.ui.features.gifdetails.contract

data class GifDetailsActions(
    val navigateUp: () -> Unit = {},
    val retry: () -> Unit = {},
)

