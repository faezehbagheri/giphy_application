package com.example.features.gifdetail.contract

data class GifDetailsActions(
    val navigateUp: () -> Unit = {},
    val retry: () -> Unit = {},
)

