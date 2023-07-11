package com.example.giphyapplication.widgets.basescreen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class EmptyStateConfig(
    val modifier: Modifier = Modifier,
    @StringRes val title: Int? = null,
    @StringRes val description: Int? = null
) {
    companion object {
        val DEFAULT = EmptyStateConfig()
    }
}

@Composable
fun EmptyStateContent(modifier: Modifier = Modifier, config: EmptyStateConfig) {

}
