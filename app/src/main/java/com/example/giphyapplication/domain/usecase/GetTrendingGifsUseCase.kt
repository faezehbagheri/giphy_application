package com.example.giphyapplication.domain.usecase

import com.example.giphyapplication.domain.repository.GifsRepository
import javax.inject.Inject

class GetTrendingGifsUseCase @Inject constructor(
    private val gifsRepository: GifsRepository
) {
    operator fun invoke() = gifsRepository.getTrendingGifs()
}