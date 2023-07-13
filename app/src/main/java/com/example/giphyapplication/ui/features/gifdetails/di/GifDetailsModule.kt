package com.example.giphyapplication.ui.features.gifdetails.di

import com.example.giphyapplication.ui.features.gifdetails.contract.GifDetailsUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GifDetailsModule {

    @Provides
    fun provideGifDetailsUiState(): GifDetailsUiState = GifDetailsUiState()
}