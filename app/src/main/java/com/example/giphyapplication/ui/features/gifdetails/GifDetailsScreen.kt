package com.example.giphyapplication.ui.features.gifdetails

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
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
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.giphyapplication.domain.model.Gif
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsEvent
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsIntent
import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsUiState
import com.example.giphyapplication.widgets.basescreen.BaseScreen
import com.example.giphyapplication.widgets.basescreen.ErrorStateConfig
import com.example.giphyapplication.widgets.utils.collectAsStateWithLifecycle
import com.example.giphyapplication.widgets.utils.collectWithLifecycle
import kotlinx.coroutines.flow.Flow
import com.example.giphyapplication.R

@ExperimentalMaterialApi
@Composable
private fun ScreenConfig(
    onIntent: (GifDetailsIntent) -> Unit,
    content: @Composable () -> Unit,
) = BaseScreen(
    padding = PaddingValues(0.dp),
    errorStateConfig = ErrorStateConfig(),
    withBackButton = true,
    onNavigateUp = {
        onIntent(GifDetailsIntent.OnNavigateUp)
    },
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

    LaunchedEffect(key1 = viewModel) {
        viewModel.acceptIntent(GifDetailsIntent.SetGifDetails(gif))
    }

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
) = ScreenConfig(onIntent) {

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

    Column(
        modifier = Modifier
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
fun HandleEvents(
    events: Flow<GifDetailsEvent>,
    navController: NavHostController,
) {
    events.collectWithLifecycle {
        when (it) {
            is GifDetailsEvent.OnNavigateUp -> {
                navController.popBackStack()
            }
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