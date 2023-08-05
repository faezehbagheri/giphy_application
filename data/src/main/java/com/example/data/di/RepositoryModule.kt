package com.example.data.di

import com.example.data.remote.GifsService
import com.example.data.repository.GifsRepositoryImpl
import com.example.domain.repository.GifsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGifsRepository(gifsRepositoryImpl: GifsRepositoryImpl): GifsRepository
}