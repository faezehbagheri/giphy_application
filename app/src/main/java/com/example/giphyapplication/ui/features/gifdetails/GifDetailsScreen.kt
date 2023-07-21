package com.example.giphyapplication.ui.features.gifdetails

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsIntent
import com.example.giphyapplication.composable.utils.collectAsStateWithLifecycle
import com.example.giphyapplication.R
import com.example.giphyapplication.composable.widgets.ErrorStateView
import com.example.giphyapplication.composable.widgets.LoadingStateView
import com.example.giphyapplication.composable.widgets.TopBar
import com.example.giphyapplication.domain.model.Gif
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsUiState

@Composable
fun GifDetailsScreen(
    id: String,
    viewModel: GifDetailsViewModel,
    onNavigateUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GifDetailsScreenLoader(
        id = id,
        uiState = uiState,
        onIntent = viewModel::acceptIntent,
        onNavigateUp = onNavigateUp,
    )
}

@Composable
private fun GifDetailsScreenLoader(
    id: String,
    uiState: GifDetailsUiState,
    onIntent: (GifDetailsIntent) -> Unit,
    onNavigateUp: () -> Unit,
) {
    LaunchedEffect(key1 = 1) {
        onIntent(GifDetailsIntent.GetGifDetails(id))
    }

    Scaffold(
        topBar = {
            TopBar(
                withBackButton = true,
                onNavigateUp = onNavigateUp,
            )
        },
        content = { innerPadding ->
            MainContent(
                modifier = Modifier
                    .padding(innerPadding),
                viewState = uiState.viewState,
                gif = uiState.gif
            )
        }
    )
}

@Composable
private fun MainContent(
    modifier: Modifier,
    viewState: GifDetailsViewState,
    gif: Gif?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.margin_normal)),
    ) {
        when (viewState) {
            is GifDetailsViewState.Loading -> {
                LoadingStateView()
            }
            is GifDetailsViewState.Error -> {
                ErrorStateView(onAction = {})
            }
            is GifDetailsViewState.Success -> {
                gif?.let {
                    GifDetails(it)
                }
            }
        }
    }
}

@Composable
private fun GifDetails(
    gif: Gif
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        painter = rememberAsyncImagePainter(gif.images.original.url, imageLoader),
        contentDescription = null,
        modifier = Modifier.aspectRatio(1f),
        contentScale = ContentScale.Fit
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_normal)))
    Text(
        text = gif.title, style = MaterialTheme.typography.body1.copy(
            color = colorResource(id = R.color.text_color),
        )
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_small)))
    KeyValueText(key = "Rating: ", value = gif.rating)
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_small)))
    KeyValueText(key = "Username: ", value = gif.username)
}

@Composable
private fun KeyValueText(
    key: String,
    value: String
) {
    Row {
        Text(text = key, color = colorResource(id = R.color.text_secondary_color))
        Text(text = value, color = colorResource(id = R.color.text_color))
    }
}

@Composable
@Preview
fun PreviewGifsListScreen() {
    GifDetailsScreen(
        viewModel = hiltViewModel(),
        onNavigateUp = {},
        id = "",
    )
}