package com.example.giphyapplication.framework

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"

@OptIn(FlowPreview::class)
abstract class BaseViewModel<UI_STATE : Parcelable, PARTIAL_UI_STATE, INTENT>(
    savedStateHandle: SavedStateHandle,
    initialState: UI_STATE
) : ViewModel() {
    private val intentFlow = MutableSharedFlow<INTENT>()

    val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, initialState)

    private val handler = CoroutineExceptionHandler { _, exception ->
        handleError(exception)
    }

    open fun handleError(exception: Throwable) {}

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(handler, block = block)

    protected suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { handleError(it) }
            .collect {
                completionHandler.invoke(it)
            }
    }

    init {
        safeLaunch {
            intentFlow
                .flatMapMerge {
                    Timber.i("mapIntent $it")
                    mapIntents(it)
                }
                .scan(uiState.value, ::reduceUiState)
                .catch { Timber.e("reduceUiState $it") }
                .collect {
                    savedStateHandle[SAVED_UI_STATE_KEY] = it
                }
        }
    }

    fun acceptIntent(intent: INTENT) =
        safeLaunch {
            intentFlow.emit(intent)
        }

    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_UI_STATE>

    protected abstract fun reduceUiState(
        previousState: UI_STATE,
        partialState: PARTIAL_UI_STATE
    ): UI_STATE
}