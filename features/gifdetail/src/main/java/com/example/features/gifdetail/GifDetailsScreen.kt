import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.domain.model.GifDetail
import com.example.features.gifdetail.GifDetailsViewModel
import com.example.features.gifdetail.contract.GifDetailsActions
import com.example.features.gifdetail.contract.GifDetailsViewState
import com.example.libraries.designsystem.R
import com.example.libraries.designsystem.composable.ErrorStateView
import com.example.libraries.designsystem.composable.LoadingStateView

@Composable
fun GifDetailsScreen(
    viewModel: GifDetailsViewModel,
    onNavigateUp: () -> Unit,
) {
    GifDetailsScreenLoader(
        viewModel = viewModel,
        onNavigateUp = onNavigateUp,
    )
}

@Composable
private fun GifDetailsScreenLoader(
    viewModel: GifDetailsViewModel,
    onNavigateUp: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val actions = GifDetailsActions(
        navigateUp = onNavigateUp,
        retry = viewModel::retry,
    )

    GifDetailsScaffold(viewState = state, actions = actions)

}

@Composable
internal fun GifDetailsScaffold(
    viewState: GifDetailsViewState,
    actions: GifDetailsActions,
) {
    Scaffold(
        topBar = {
            com.example.libraries.designsystem.composable.TopBar(
                withBackButton = true,
                onNavigateUp = actions.navigateUp,
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.margin_normal)),
            ) {
                when (viewState) {
                    is GifDetailsViewState.Loading -> {
                        LoadingStateView()
                    }
                    is GifDetailsViewState.Error -> {
                        ErrorStateView(onRetry = actions.retry)
                    }
                    is GifDetailsViewState.Result -> {
                        GifDetails(viewState.gif)
                    }
                }
            }
        }
    )
}

@Composable
private fun GifDetails(gif: GifDetail) {
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
        painter = rememberAsyncImagePainter(gif.gif, imageLoader),
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
    )
}