package com.example.giphyapplication.domain.di

import com.example.giphyapplication.domain.repository.GifsRepository
import com.example.giphyapplication.domain.usecase.GetTrendingGifsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetTrendingGifsUseCase(
        gifsRepository: GifsRepository
    ): GetTrendingGifsUseCase {
        return GetTrendingGifsUseCase(gifsRepository)
    }
}


















