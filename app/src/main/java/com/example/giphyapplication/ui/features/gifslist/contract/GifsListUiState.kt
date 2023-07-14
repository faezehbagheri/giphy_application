package com.example.giphyapplication.ui.features.gifslist.contract

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.giphyapplication.domain.model.Gif
import com.example.giphyapplication.ui.features.gifslist.utils.ListState
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class GifsListUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val errorMessage: String = "",
    val gifsList: List<Gif> = listOf(),
    val page: Int = 1,
    val canPaginate: Boolean = true,
    val listState: ListState = ListState.IDLE,
) : Parcelable {
    sealed class PartialState {
        object Loading : PartialState()
        data class Error(val msg: String = "", val show: Boolean = false) : PartialState()
        object NetworkError : PartialState()
        data class OnReceivedGiftsList(val gifs: List<Gif>) : PartialState()
        data class OnPageChanged(val page: Int) : PartialState()
        data class OnCanPaginationChanged(val value: Boolean) : PartialState()
        data class OnListStateChanged(val state: ListState) : PartialState()
    }
}
