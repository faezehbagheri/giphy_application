package com.example.features.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.features.search.R

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.searchBar(
    searchText: String,
    requestFocus: Boolean = false,
    onSearch: (String) -> Unit,
) {
    stickyHeader {
        Box(
            modifier = Modifier
                .background(colorResource(id = R.color.background))
                .padding(
                    vertical = dimensionResource(id = R.dimen.margin_normal),
                    horizontal = dimensionResource(id = R.dimen.margin_small),
                )
        ) {
            val focusManager = LocalFocusManager.current
            val focusRequester = remember { FocusRequester() }

            if (requestFocus) {
                LaunchedEffect(requestFocus) {
                    focusRequester.requestFocus()
                }
            }

            var isFocused by remember { mutableStateOf(false) }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_normal))
                    )
                    .padding(horizontal = 8.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = it.isFocused },
                value = searchText,
                onValueChange = {
                    if (!it.contains("\n")) {
                        onSearch(it)
                    }
                },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.onBackground,
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "searchIcon",
                        tint = Color.Gray
                    )
                },
                placeholder = {
                    AnimatedVisibility(visible = isFocused.not()) {
                        Text(text = stringResource(R.string.searchBarHint), color = Color.LightGray)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        searchBar(searchText = "", onSearch = {})
    }
}