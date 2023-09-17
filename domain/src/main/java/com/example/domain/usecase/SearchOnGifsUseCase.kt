package com.example.domain.usecase

import com.example.domain.repository.GifsRepository
import javax.inject.Inject

class SearchOnGifsUseCase @Inject constructor(
    private val gifsRepository: GifsRepository
) {
    operator fun invoke(searchTerms: String) = gifsRepository.searchOnGifs(searchTerms)
}