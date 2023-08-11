package com.example.domain.usecase

import com.example.domain.repository.GifsRepository
import javax.inject.Inject

class GetTrendingGifsUseCase @Inject constructor(
    private val gifsRepository: GifsRepository
) {
    operator fun invoke() = gifsRepository.getTrendingGifs()
}