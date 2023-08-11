package com.example.libraries.utils.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
fun ViewModel.safeLaunch(block: suspend CoroutineScope.() -> Unit) =
    viewModelScope.launch(exceptionHandler, block = block)
