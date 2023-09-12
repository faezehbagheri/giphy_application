package com.example.features.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.features.search.R


@Composable
fun SearchBar(
    searchText: String,
    onSearch: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.margin_small),
                vertical = dimensionResource(id = R.dimen.margin_normal),
            )
            .height(44.dp)
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = { newText: String ->
                    onSearch(newText)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .padding(dimensionResource(id = R.dimen.margin10)),
                textStyle = MaterialTheme.typography.body1.copy(
                    color = Color.Black,
                ),
                cursorBrush = Brush.verticalGradient(
                    0f to MaterialTheme.colors.primary,
                    1f to MaterialTheme.colors.primary
                ),
                singleLine = true,
                interactionSource = interactionSource,
            )
            if (searchText.isEmpty()) {
                Text(
                    text = "Search all the GIFs", style = MaterialTheme.typography.body1.copy(
                        color = Color.LightGray,
                    ),
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.margin10))
                )
            }
        }
        Box(
            modifier = Modifier
                .height(44.dp)
                .width(50.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(id = R.color.pink),
                            colorResource(id = R.color.purple),
                            colorResource(id = R.color.violet),
                        ),
                        start = Offset(Float.POSITIVE_INFINITY, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        SearchBar(searchText = "", onSearch = {})
    }
}