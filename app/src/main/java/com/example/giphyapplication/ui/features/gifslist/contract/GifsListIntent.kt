package com.example.giphyapplication.ui.features.gifslist.contract

import androidx.paging.PagingData
import com.example.giphyapplication.domain.model.Gif
import com.example.giphyapplication.ui.features.gifslist.utils.ListState
import kotlinx.coroutines.flow.Flow

sealed class GifsListIntent {
    data class OnReceivedGiftsList(val gifs: Flow<PagingData<Gif>>) : GifsListIntent()
}
