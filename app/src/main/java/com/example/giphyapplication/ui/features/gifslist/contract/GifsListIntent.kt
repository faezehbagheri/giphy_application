package com.example.giphyapplication.ui.features.gifslist.contract

import com.example.giphyapplication.domain.model.Gif
import com.example.giphyapplication.ui.features.gifslist.utils.ListState

sealed class GifsListIntent{
    object FetchGifsList : GifsListIntent()
    data class NavigateToGifDetails(val id: String) : GifsListIntent()
    data class OnReceivedGiftsList(val gifs: List<Gif>) : GifsListIntent()
    data class OnPageChanged(val page: Int) : GifsListIntent()
    data class OnCanPaginationChanged(val value: Boolean) : GifsListIntent()
    data class OnListStateChanged(val state: ListState) : GifsListIntent()
}
