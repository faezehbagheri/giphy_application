package com.example.giphyapplication.ui.features.gifslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListIntent
import com.example.giphyapplication.composable.utils.collectAsStateWithLifecycle
import com.example.giphyapplication.composable.widgets.ErrorStateView
import com.example.giphyapplication.composable.widgets.InitialStateView
import com.example.giphyapplication.composable.widgets.LoadingStateView
import com.example.giphyapplication.composable.widgets.TopBar
import com.example.giphyapplication.domain.model.Gif
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListUiState

@Composable
fun GifsListScreen(
    viewModel: GifsListViewModel,
    onNavigateToGifsDetails: (id: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GifsListScreenLoader(
        uiState = uiState,
        onIntent = viewModel::acceptIntent,
        onNavigateToGifsDetails = onNavigateToGifsDetails,
    )
}

@Composable
private fun GifsListScreenLoader(
    uiState: GifsListUiState,
    onIntent: (GifsListIntent) -> Unit,
    onNavigateToGifsDetails: (id: String) -> Unit,
) {
    val lazyPagingItems = uiState.pagingGifs?.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopBar(withBackButton = false) },
        content = { innerPadding ->
            MainContent(
                modifier = Modifier.padding(innerPadding),
                lazyPagingItems = lazyPagingItems,
                onNavigateToGifsDetails = onNavigateToGifsDetails
            )
        }
    )
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<Gif>?,
    onNavigateToGifsDetails: (String) -> Unit
) {
    val refreshLoadState = lazyPagingItems?.loadState?.refresh

    if (lazyPagingItems == null) {
        InitialStateView()
    } else if (refreshLoadState is LoadState.Loading) {
        LoadingStateView()
    } else if (refreshLoadState is LoadState.Error) {
        ErrorStateView(
            onAction = { lazyPagingItems.retry() },
        )
    } else {
        GifsList(
            modifier = modifier,
            lazyPagingItems = lazyPagingItems,
            onNavigateToGifsDetails = onNavigateToGifsDetails
        )
    }
}

@Composable
private fun GifsList(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<Gif>,
    onNavigateToGifsDetails: (String) -> Unit
) {
    val lazyListState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        state = lazyListState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        items(
            count = lazyPagingItems.itemCount,
        ) { index ->
            val gif = lazyPagingItems[index]
            gif?.let {
                GifItem(it, onNavigateToGifsDetails)
            }
        }
    }
}

@Composable
private fun GifItem(
    gif: Gif,
    onNavigateToGifsDetails: (String) -> Unit
) {
    Image(
        painter = rememberAsyncImagePainter(gif.images.original.url),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .aspectRatio(1f)
            .clickable {
                onNavigateToGifsDetails(gif.id)
            }
    )
}

@Composable
@Preview
fun PreviewGifsListScreen() {
    GifsListScreen(
        viewModel = hiltViewModel(),
        onNavigateToGifsDetails = {},
    )
}