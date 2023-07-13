package com.example.giphyapplication.ui.features.gifdetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.giphyapplication.domain.model.Gif
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsEvent
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsIntent
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsUiState
import com.example.giphyapplication.widgets.basescreen.BaseScreen
import com.example.giphyapplication.widgets.basescreen.ErrorStateConfig
import com.example.giphyapplication.widgets.utils.collectAsStateWithLifecycle
import com.example.giphyapplication.widgets.utils.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@ExperimentalMaterialApi
@Composable
private fun ScreenConfig(
    content: @Composable () -> Unit
) = BaseScreen(
    padding = PaddingValues(0.dp),
    errorStateConfig = ErrorStateConfig(),
    content = content
)

@ExperimentalMaterialApi
@Composable
fun GifDetailsRoute(
    gif: Gif,
    viewModel: GifDetailsViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HandleEvents(
        viewModel.event,
        navController,
    )

    GifDetailsScreen(
        uiState = uiState,
        onIntent = viewModel::acceptIntent,
    )

}

@ExperimentalMaterialApi
@Composable
fun GifDetailsScreen(
    uiState: GifDetailsUiState,
    onIntent: (GifDetailsIntent) -> Unit,
) = ScreenConfig() {

}

@Composable
fun HandleEvents(
    events: Flow<GifDetailsEvent>,
    navController: NavHostController,
) {
    events.collectWithLifecycle {
        when (it) {
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun PreviewGifsListScreen() {
    GifDetailsScreen(
        uiState = GifDetailsUiState(),
        onIntent = {}
    )
}