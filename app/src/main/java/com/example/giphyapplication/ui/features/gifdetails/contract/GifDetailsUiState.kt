package com.example.giphyapplication.ui.features.gifdetails.contract

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.giphyapplication.domain.model.Gif
import com.example.giphyapplication.ui.features.gifdetails.GifDetailsViewState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
@Parcelize
data class GifDetailsUiState(
    val viewState: @RawValue GifDetailsViewState = GifDetailsViewState.Loading,
    val gif: Gif? = null
) : Parcelable {
    sealed class PartialState {
        data class OnViewStateChanged(val viewState: GifDetailsViewState) : PartialState()
        data class OnGifReceived(val gif: Gif) : PartialState()
    }
}
