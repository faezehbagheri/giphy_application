package com.example.giphyapplication.ui.features.gifslist.contract

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class GifsListUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val errorMessage: String = "",
) : Parcelable {
    sealed class PartialState {
        object Loading : PartialState()
        data class Error(val msg: String = "", val show: Boolean = false) : PartialState()
        object NetworkError : PartialState()
    }
}
