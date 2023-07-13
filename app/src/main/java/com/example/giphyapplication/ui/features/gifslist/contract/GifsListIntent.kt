package com.example.giphyapplication.ui.features.gifslist.contract

sealed class GifsListIntent{
    object FetchGifsList : GifsListIntent()
    data class NavigateToGifDetails(val id: String) : GifsListIntent()
}
