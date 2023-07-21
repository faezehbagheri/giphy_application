package com.example.giphyapplication.ui.features.gifslist.contract

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.example.giphyapplication.domain.model.Gif
import kotlinx.coroutines.flow.Flow
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
@Parcelize
data class GifsListUiState(
    val pagingGifs: @RawValue Flow<PagingData<Gif>>? = null,
) : Parcelable {
    sealed class PartialState {
        data class OnReceivedGiftsList(val gifs: Flow<PagingData<Gif>>) : PartialState()
    }
}
