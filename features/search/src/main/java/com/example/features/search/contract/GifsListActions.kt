package com.example.features.search.contract

data class GifsListActions(
    val navigateToDetails: (gifId: String) -> Unit = {},
    val onSearch: (searchTerms: String) -> Unit = {}
)
