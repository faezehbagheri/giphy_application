package com.example.domain.usecase

import com.example.domain.repository.GifsRepository
import javax.inject.Inject

class GetGifDetailUseCase @Inject constructor(
    private val gifsRepository: GifsRepository
) {
    operator fun invoke(id: String) = gifsRepository.getGifDetail(id)
}
