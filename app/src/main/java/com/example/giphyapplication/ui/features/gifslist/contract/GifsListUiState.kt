package com.example.giphyapplication.ui.features.gifslist.contract

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.giphyapplication.domain.model.Gif
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class GifsListUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val errorMessage: String = "",
    val gifsList: List<Gif> = listOf()
) : Parcelable {
    sealed class PartialState {
        object Loading : PartialState()
        data class Error(val msg: String = "", val show: Boolean = false) : PartialState()
        object NetworkError : PartialState()
        data class ReceivedGiftsList(val gifs: List<Gif>) : PartialState()
    }
}
