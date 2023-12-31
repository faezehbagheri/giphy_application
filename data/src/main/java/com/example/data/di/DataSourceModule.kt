package com.example.data.di

import com.example.data.datasource.GifDataSource
import com.example.data.datasource.GifDataSourceImpl
import com.example.data.remote.GifService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun bindGifsDataSource(gifService: GifService): GifDataSource =
        GifDataSourceImpl(
            gifService = gifService,
        )
}
