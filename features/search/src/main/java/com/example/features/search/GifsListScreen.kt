package com.example.features.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.Gif
import com.example.features.search.contract.GifsListActions
import com.example.features.search.contract.GifsListViewState
import com.example.libraries.designsystem.R

@Composable
fun GifsListScreen(
    viewModel: GifsListViewModel,
    onNavigateToGifsDetails: (id: String) -> Unit,
) {
    GifsListScreenLoader(
        viewModel = viewModel,
        onNavigateToGifsDetails = onNavigateToGifsDetails,
    )
}

@Composable
private fun GifsListScreenLoader(
    viewModel: GifsListViewModel,
    onNavigateToGifsDetails: (id: String) -> Unit,
) {

    val viewState by viewModel.uiState.collectAsState()
    val actions = GifsListActions(
        navigateToDetails = onNavigateToGifsDetails
    )

    GifsListScaffold(viewState, actions)
}

@Composable
internal fun GifsListScaffold(
    viewState: GifsListViewState,
    actions: GifsListActions
) {
    Scaffold(
        topBar = { com.example.libraries.designsystem.composable.TopBar(withBackButton = false) },
    ) { innerPadding ->
        val lazyPagingItems = viewState.pagingData?.collectAsLazyPagingItems()
        MainContent(
            modifier = Modifier.padding(innerPadding),
            lazyPagingItems = lazyPagingItems,
            onNavigateToGifsDetails = actions.navigateToDetails
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<Gif>?,
    onNavigateToGifsDetails: (String) -> Unit
) {
    val refreshLoadState = lazyPagingItems?.loadState?.refresh

    if (lazyPagingItems == null) {
        com.example.libraries.designsystem.composable.InitialStateView()
    } else if (refreshLoadState is LoadState.Loading) {
        com.example.libraries.designsystem.composable.LoadingStateView()
    } else if (refreshLoadState is LoadState.Error) {
        com.example.libraries.designsystem.composable.ErrorStateView(
            onRetry = { lazyPagingItems.retry() },
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
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.margin_normal)),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.grid_space),
            Alignment.CenterHorizontally
        ),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.grid_space),
            Alignment.CenterVertically
        )
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