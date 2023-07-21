package com.example.giphyapplication.ui.features.gifdetails.contract

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.giphyapplication.domain.model.Gif
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class GifDetailsUiState(
    val gif: Gif? = null
) : Parcelable {
    sealed class PartialState {
        data class SetGifDetails(val id: String) : PartialState()
    }
}
