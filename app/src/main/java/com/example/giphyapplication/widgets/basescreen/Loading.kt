package com.example.giphyapplication.widgets.basescreen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

data class LoadingConfig(
    val modifier: Modifier = Modifier,
    val shown: Boolean = true,
    @StringRes val title: Int? = null,
) {
    companion object {
        val DEFAULT = LoadingConfig()
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier, config: LoadingConfig) {

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

    }
    
}
