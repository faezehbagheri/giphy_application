package com.example.giphyapplication.composable.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.giphyapplication.R

@Composable
fun TopBar(
    withBackButton: Boolean,
    onNavigateUp: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = R.drawable.giphy_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
            )
            if (withBackButton) {
                IconButton(onClick = onNavigateUp) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_forward),
                        contentDescription = null
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            colorResource(id = R.color.red),
                            colorResource(id = R.color.yellow),
                            colorResource(id = R.color.green),
                            colorResource(id = R.color.blue),
                            colorResource(id = R.color.purple),
                        )
                    )
                ),
            thickness = 2.dp
        )
    }
}