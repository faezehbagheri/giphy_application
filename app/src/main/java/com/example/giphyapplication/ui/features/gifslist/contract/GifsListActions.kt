package com.example.giphyapplication.ui.features.gifslist.contract

data class GifsListActions(
    val navigateToDetails: (gifId: String) -> Unit = {}
)
