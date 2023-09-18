package com.example.features.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.Gif
import com.example.features.search.components.SearchBar
import com.example.features.search.contract.GifsListActions
import com.example.features.search.contract.GifsListViewState
import com.example.libraries.designsystem.composable.EmptyStateView
import com.example.libraries.designsystem.composable.ErrorStateView
import com.example.libraries.designsystem.composable.LoadingStateView
import com.example.libraries.designsystem.composable.TopBar

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
        navigateToDetails = onNavigateToGifsDetails,
        onSearch = viewModel::search
    )

    GifsListScaffold(viewState, actions)
}

@Composable
internal fun GifsListScaffold(
    viewState: GifsListViewState,
    actions: GifsListActions
) {
    Scaffold(
        topBar = { TopBar(withBackButton = false) },
        backgroundColor = MaterialTheme.colors.background
    ) { innerPadding ->
        val lazyPagingItems = viewState.pagingData?.collectAsLazyPagingItems()
        MainContent(
            modifier = Modifier.padding(innerPadding),
            state = viewState,
            lazyPagingItems = lazyPagingItems,
            onNavigateToGifsDetails = actions.navigateToDetails,
            onSearch = actions.onSearch
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    state: GifsListViewState,
    lazyPagingItems: LazyPagingItems<Gif>?,
    onNavigateToGifsDetails: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    val refreshLoadState = lazyPagingItems?.loadState?.refresh

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(searchText = state.searchTerms, onSearch = onSearch)
        lazyPagingItems?.let {
            when {
                refreshLoadState is LoadState.Loading -> {
                    LoadingStateView()
                }

                refreshLoadState is LoadState.Error -> {
                    ErrorStateView(
                        onRetry = { lazyPagingItems.retry() },
                    )
                }

                lazyPagingItems.itemCount == 0 -> {
                    EmptyStateView()
                }

                else -> {
                    GifsList(
                        modifier = modifier,
                        lazyPagingItems = lazyPagingItems,
                        onNavigateToGifsDetails = onNavigateToGifsDetails
                    )
                }
            }
        }
    }
}

@Composable
private fun GifsList(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<Gif>,
    onNavigateToGifsDetails: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.testTag("gifsList"),
        columns = GridCells.Fixed(2),
    ) {
        items(count = lazyPagingItems.itemCount) { index ->
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
        painter = rememberAsyncImagePainter(gif.thumbnail),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .testTag(gif.id)
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
