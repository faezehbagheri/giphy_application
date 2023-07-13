package com.example.giphyapplication.ui.features.gifslist

import FlowRow
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListEvent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListIntent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListUiState
import com.example.giphyapplication.widgets.basescreen.BaseScreen
import com.example.giphyapplication.widgets.basescreen.ErrorStateConfig
import com.example.giphyapplication.widgets.utils.collectAsStateWithLifecycle
import com.example.giphyapplication.widgets.utils.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@ExperimentalMaterialApi
@Composable
private fun ScreenConfig(
    uiState: GifsListUiState,
    content: @Composable () -> Unit
) = BaseScreen(
    isLoading = uiState.isLoading,
    isError = uiState.isError,
    isEmpty = uiState.isEmpty,
    padding = PaddingValues(0.dp),
    errorStateConfig = ErrorStateConfig(),
    content = content
)

@ExperimentalMaterialApi
@Composable
fun GifsListRoute(
    viewModel: GifsListViewModel,
    navController: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HandleEvents(
        viewModel.event,
        navController,
    )

    GifsListScreen(
        uiState = uiState,
        onIntent = viewModel::acceptIntent,
    )

}

@ExperimentalMaterialApi
@Composable
fun GifsListScreen(
    uiState: GifsListUiState,
    onIntent: (GifsListIntent) -> Unit,
) = ScreenConfig(uiState = uiState) {

    val configuration = LocalConfiguration.current
    val size = (configuration.screenWidthDp - 42) / 2

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            FlowRow(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                mainAxisSpacing = 10.dp,
                crossAxisSpacing = 10.dp
            ) {
                uiState.gifsList.forEach {
                    Image(
                        painter = rememberAsyncImagePainter(it.images.original.url),
                        contentDescription = null,
                        modifier = Modifier
                            .size(size.dp)
                            .clickable {
                                onIntent(GifsListIntent.NavigateToGifDetails(it.id))
                            }

                    )
                }
            }
        }
    }
}

@Composable
fun HandleEvents(
    events: Flow<GifsListEvent>,
    navController: NavHostController,
) {
    events.collectWithLifecycle {
        when (it) {
            is GifsListEvent.NavigateToGifDetails -> {
                navController.navigate("gif_details/${it.id}")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun PreviewGifsListScreen() {
    GifsListScreen(
        uiState = GifsListUiState(),
        onIntent = {}
    )
}