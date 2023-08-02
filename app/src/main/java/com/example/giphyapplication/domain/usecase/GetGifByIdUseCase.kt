package com.example.giphyapplication.domain.usecase

import com.example.giphyapplication.domain.repository.GifsRepository
import javax.inject.Inject

class GetGifByIdUseCase @Inject constructor(
    private val gifsRepository: GifsRepository
) {
    operator fun invoke(id: String) = gifsRepository.getGifById(id)
}