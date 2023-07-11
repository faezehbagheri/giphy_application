package com.example.giphyapplication.widgets.basescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class ErrorStateConfig(
    val title: String = ""
) {
    companion object {
        val DEFAULT = ErrorStateConfig()
    }
}

@Composable
fun ErrorStateContent(modifier: Modifier = Modifier, config: ErrorStateConfig) {

}
