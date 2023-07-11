package com.example.giphyapplication.ui.features.gifslist.di

import com.example.giphyapplication.ui.features.gifslist.contract.GifsListUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GifsListModule {

    @Provides
    fun provideGifsListUiState(): GifsListUiState = GifsListUiState()
}