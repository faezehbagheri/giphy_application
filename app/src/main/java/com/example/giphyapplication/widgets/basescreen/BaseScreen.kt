package com.example.giphyapplication.widgets.basescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.giphyapplication.R

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(16.dp),
    isLoading: Boolean = false,
    isEmpty: Boolean = false,
    isError: Boolean = false,
    withBackButton: Boolean = false,
    emptyStateConfig: EmptyStateConfig = EmptyStateConfig.DEFAULT,
    errorStateConfig: ErrorStateConfig = ErrorStateConfig.DEFAULT,
    loadingConfig: LoadingConfig = LoadingConfig.DEFAULT,
    onNavigateUp: () -> Unit = {},
    content: @Composable () -> Unit
) {

    Scaffold(
        contentColor = MaterialTheme.colors.surface,
        backgroundColor = MaterialTheme.colors.background,
        topBar = { TopBar(withBackButton, onNavigateUp) }
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

@Composable
fun TopBar(
    withBackButton: Boolean,
    onNavigateUp: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = painterResource(id = R.drawable.giphy_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
            )
            if (withBackButton) {
                IconButton(onClick = onNavigateUp) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = null
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            colorResource(id = R.color.red),
                            colorResource(id = R.color.yellow),
                            colorResource(id = R.color.green),
                            colorResource(id = R.color.blue),
                            colorResource(id = R.color.purple),
                        )
                    )
                ),
            thickness = 2.dp
        )
    }
}