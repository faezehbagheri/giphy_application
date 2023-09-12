package com.example.features.search.contract

import androidx.paging.PagingData
import com.example.domain.model.Gif
import kotlinx.coroutines.flow.Flow

data class GifsListViewState(
    val pagingData: Flow<PagingData<Gif>>? = null,
    val isLoading: Boolean = true,
    val searchTerms: String = "",
)
