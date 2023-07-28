package com.example.giphyapplication.ui.features.gifslist.contract

import androidx.paging.PagingData
import com.example.giphyapplication.domain.model.Gif
import kotlinx.coroutines.flow.Flow

data class GifsListViewState(
    val pagingData: Flow<PagingData<Gif>>? = null,
    val isLoading: Boolean = true,
)
