package com.example.giphyapplication.ui.gifdetails.contract

data class GifDetailsActions(
    val navigateUp: () -> Unit = {},
    val retry: () -> Unit = {},
)

