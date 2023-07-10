package com.example.giphyapplication.data.di

import com.example.giphyapplication.data.remote.GifsService
import com.example.giphyapplication.data.repository.GifsRepositoryImpl
import com.example.giphyapplication.domain.repository.GifsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideGifsRepository(
        gifsService: GifsService,
    ): GifsRepository {
        return GifsRepositoryImpl(
            gifsService,
        )
    }
}