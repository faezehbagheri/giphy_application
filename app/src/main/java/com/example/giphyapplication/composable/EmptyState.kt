package com.example.giphyapplication.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.giphyapplication.R

@Composable
fun EmptyStateView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.giphy_logo),
            contentDescription = null,
        )
        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.spacer_height_normal)),
        )
        Text(
            stringResource(id = R.string.no_data),
            style = MaterialTheme.typography.body2.copy(
                color = colorResource(id = R.color.text_color)
            ),
        )
    }
}

@Composable
@Preview
fun PreviewEmptyStateView() {
    EmptyStateView()
}
