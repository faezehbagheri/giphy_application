package com.example.giphyapplication.widgets.basescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(16.dp),
    isLoading: Boolean = false,
    isEmpty: Boolean = false,
    isError: Boolean = false,
    emptyStateConfig: EmptyStateConfig = EmptyStateConfig.DEFAULT,
    errorStateConfig: ErrorStateConfig = ErrorStateConfig.DEFAULT,
    loadingConfig: LoadingConfig = LoadingConfig.DEFAULT,
    content: @Composable () -> Unit
) {

    Scaffold(
        contentColor = MaterialTheme.colors.surface,
        backgroundColor = MaterialTheme.colors.background,
    ) { it ->
        Box(
            modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                isLoading -> Loading(config = loadingConfig)
                isEmpty -> EmptyStateContent(config = emptyStateConfig)
                isError -> ErrorStateContent(config = errorStateConfig)
            }
            content()
        }
    }

}