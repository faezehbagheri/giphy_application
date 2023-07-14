package com.example.giphyapplication.ui.features.gifslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListEvent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListIntent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListUiState
import com.example.giphyapplication.ui.features.gifslist.utils.ListState
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

    val lazyColumnListState = rememberLazyGridState()
    val shouldStartPaginate = remember {
        derivedStateOf {
            uiState.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && uiState.listState == ListState.IDLE)
            onIntent(GifsListIntent.FetchGifsList)
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColumnListState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        itemsIndexed(
            items = uiState.gifsList,
        ) { _, gif ->
            Image(
                painter = rememberAsyncImagePainter(gif.images.original.url),
                contentDescription = null,
                modifier = Modifier
                    .size(size.dp)
                    .clickable {
                        onIntent(GifsListIntent.NavigateToGifDetails(gif.id))
                    }

            )
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