package com.example.features.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.Gif
import com.example.features.search.components.VerticalGrid
import com.example.features.search.components.searchBar
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
    Scaffold { innerPadding ->
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

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        topBar()
        searchBar(searchText = state.searchTerms, onSearch = onSearch)
        lazyPagingItems?.let {
            when {
                refreshLoadState is LoadState.Loading -> loading()
                refreshLoadState is LoadState.Error -> error { lazyPagingItems.retry() }
                lazyPagingItems.itemCount == 0 -> empty()
                else -> gifsList(it, onNavigateToGifsDetails)
            }
        }
    }
}

private fun LazyListScope.topBar() {
    item { TopBar(withBackButton = false) }
}

private fun LazyListScope.loading() {
    item {
        Box(modifier = Modifier.fillParentMaxSize()) {
            LoadingStateView()
        }
    }
}

private fun LazyListScope.error(retry: () -> Unit) {
    item {
        Box(modifier = Modifier.fillParentMaxSize()) {
            ErrorStateView(onRetry = { retry() })
        }
    }
}

private fun LazyListScope.empty() {
    item {
        Box(modifier = Modifier.fillParentMaxSize()) {
            EmptyStateView()
        }
    }
}

private fun LazyListScope.gifsList(
    lazyPagingItems: LazyPagingItems<Gif>,
    onNavigateToGifsDetails: (String) -> Unit,
) {
    item {
        VerticalGrid(
            columns = 2
        ) {
            lazyPagingItems.itemSnapshotList.items.forEach {
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
            .clip(RoundedCornerShape(8.dp))
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
