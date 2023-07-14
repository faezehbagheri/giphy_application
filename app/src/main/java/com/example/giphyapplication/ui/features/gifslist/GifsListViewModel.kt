package com.example.giphyapplication.ui.features.gifslist

import androidx.lifecycle.SavedStateHandle
import com.example.giphyapplication.common.result.GetResult
import com.example.giphyapplication.domain.usecase.GetTrendingGifsUseCase
import com.example.giphyapplication.framework.BaseViewModel
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListEvent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListIntent
import com.example.giphyapplication.ui.features.gifslist.contract.GifsListUiState
import com.example.giphyapplication.ui.features.gifslist.utils.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class GifsListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    initialState: GifsListUiState,
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase,
) : BaseViewModel<GifsListUiState, GifsListUiState.PartialState, GifsListEvent, GifsListIntent>(
    savedStateHandle,
    initialState
) {

    init {
        acceptIntent(GifsListIntent.FetchGifsList)
    }

    override fun mapIntents(intent: GifsListIntent): Flow<GifsListUiState.PartialState> =
        when (intent) {
            is GifsListIntent.FetchGifsList -> flow {
                getNews()
            }
            is GifsListIntent.NavigateToGifDetails -> flow {
                publishEvent(GifsListEvent.NavigateToGifDetails(intent.id))
            }
            is GifsListIntent.OnReceivedGiftsList -> flow {
                emit(GifsListUiState.PartialState.OnReceivedGiftsList(intent.gifs))
            }
            is GifsListIntent.OnPageChanged -> flow {
                emit(GifsListUiState.PartialState.OnPageChanged(intent.page))
            }
            is GifsListIntent.OnCanPaginationChanged -> flow {
                emit(GifsListUiState.PartialState.OnCanPaginationChanged(intent.value))
            }
            is GifsListIntent.OnListStateChanged -> flow {
                emit(GifsListUiState.PartialState.OnListStateChanged(intent.state))
            }
        }

    override fun reduceUiState(
        previousState: GifsListUiState,
        partialState: GifsListUiState.PartialState
    ): GifsListUiState = when (partialState) {
        is GifsListUiState.PartialState.Error -> previousState.copy(
            isError = partialState.show,
            errorMessage = partialState.msg,
            isLoading = false,
            isEmpty = false
        )
        GifsListUiState.PartialState.Loading -> previousState.copy(
            isLoading = true, isError = false, isEmpty = false
        )
        GifsListUiState.PartialState.NetworkError -> previousState.copy(
            isLoading = false, isError = true, isEmpty = false
        )
        is GifsListUiState.PartialState.OnReceivedGiftsList -> {
            previousState.copy(
                gifsList = partialState.gifs.toMutableList(),
                isLoading = false,
                isError = false,
                isEmpty = partialState.gifs.isEmpty()
            )
        }
        is GifsListUiState.PartialState.OnPageChanged -> {
            previousState.copy(page = partialState.page)
        }
        is GifsListUiState.PartialState.OnCanPaginationChanged -> {
            previousState.copy(canPaginate = partialState.value)
        }
        is GifsListUiState.PartialState.OnListStateChanged -> {
            previousState.copy(listState = partialState.state)
        }
    }

    private fun getNews() = safeLaunch {
        val page = uiState.value.page
        val canPaginate = uiState.value.canPaginate
        val listState = uiState.value.listState
        val gifsList = uiState.value.gifsList.toMutableList()

        if (page == 1 || (page != 1 && canPaginate) && listState != ListState.LOADING) {

            call(getTrendingGifsUseCase(page)) { result ->
                when (result) {
                    is GetResult.Success -> {
                        result.data?.let { data ->
                            acceptIntent(GifsListIntent.OnCanPaginationChanged(data.size == 15))

                            if (page == 1) {
                                gifsList.clear()
                                gifsList.addAll(data)
                                acceptIntent(GifsListIntent.OnReceivedGiftsList(gifsList))
                            } else {
                                gifsList.addAll(data)
                                acceptIntent(GifsListIntent.OnReceivedGiftsList(gifsList))
                            }

                            acceptIntent(GifsListIntent.OnListStateChanged(ListState.IDLE))

                            if (canPaginate) {
                                acceptIntent(GifsListIntent.OnPageChanged(page + 1))
                            }
                        }
                    }
                    is GetResult.Error -> {
                        acceptIntent(GifsListIntent.OnListStateChanged(ListState.ERROR))
                    }
                    is GetResult.Loading -> {
                        if (page != 1)
                            acceptIntent(GifsListIntent.OnListStateChanged(ListState.LOADING))
                    }
                }
            }
        }
    }
}