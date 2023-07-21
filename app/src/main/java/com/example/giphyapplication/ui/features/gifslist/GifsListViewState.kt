package com.example.giphyapplication.ui.features.gifslist

import com.example.giphyapplication.domain.model.Gif

sealed class GifsListViewState {
    object Loading : GifsListViewState()
    object Error : GifsListViewState()
    object Success : GifsListViewState()
}
