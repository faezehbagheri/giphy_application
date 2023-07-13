package com.example.giphyapplication.ui.features.gifslist.contract

sealed class GifsListEvent {
    data class NavigateToGifDetails(val id: String) : GifsListEvent()
}
