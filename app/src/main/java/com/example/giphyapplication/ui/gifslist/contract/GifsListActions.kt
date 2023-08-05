package com.example.giphyapplication.ui.gifslist.contract

data class GifsListActions(
    val navigateToDetails: (gifId: String) -> Unit = {}
)
