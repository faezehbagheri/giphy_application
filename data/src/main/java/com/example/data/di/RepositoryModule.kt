package com.example.data.di

import com.example.data.repository.GifsRepositoryImpl
import com.example.domain.repository.GifsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGifsRepository(gifsRepositoryImpl: GifsRepositoryImpl): GifsRepository
}
