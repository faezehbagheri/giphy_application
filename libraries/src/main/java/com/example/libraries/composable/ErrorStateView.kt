package com.example.libraries.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.libraries.R

@Composable
fun ErrorStateView(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(onClick = onRetry) {
            Icon(
                painter = painterResource(id = R.drawable.ic_loop),
                contentDescription = null,
                tint = colorResource(id = R.color.on_background)
            )
        }
        Text(
            stringResource(id = R.string.retry),
            style = MaterialTheme.typography.body2.copy(
                color = colorResource(id = R.color.text_color)
            ),
        )
    }
}

@Composable
@Preview
fun PreviewErrorStateView() {
    ErrorStateView(
        onRetry = {}
    )
}
