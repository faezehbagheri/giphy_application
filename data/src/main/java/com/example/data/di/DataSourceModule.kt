package com.example.data.di

import com.example.data.datasource.GifDataSource
import com.example.data.datasource.GifDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindGifsDataSource(gifDataSourceImpl: GifDataSourceImpl): GifDataSource
}
