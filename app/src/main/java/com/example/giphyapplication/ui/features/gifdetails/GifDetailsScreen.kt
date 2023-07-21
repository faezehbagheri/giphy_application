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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsIntent
import com.example.giphyapplication.composable.utils.collectAsStateWithLifecycle
import com.example.giphyapplication.R
import com.example.giphyapplication.composable.widgets.TopBar
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

    val configuration = LocalConfiguration.current
    val size = configuration.screenWidthDp - 32

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Scaffold(
        topBar = {
            TopBar(
                withBackButton = true,
                onNavigateUp = onNavigateUp,
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                uiState.gif?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it.images.original.url, imageLoader),
                        contentDescription = null,
                        modifier = Modifier.size(size.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it.title, style = MaterialTheme.typography.body1.copy(
                            color = colorResource(id = R.color.text_color),
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    KeyValueText(key = "Rating: ", value = it.rating)
                    Spacer(modifier = Modifier.height(8.dp))
                    KeyValueText(key = "Username: ", value = it.username)
                }
            }
        }
    )
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