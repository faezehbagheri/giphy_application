package com.example.giphyapplication.ui.features.gifslist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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

}

@Composable
fun HandleEvents(
    events: Flow<GifsListEvent>,
    navController: NavHostController,
) {
    events.collectWithLifecycle {
        when (it) {
            else -> {}
        }
    }
}