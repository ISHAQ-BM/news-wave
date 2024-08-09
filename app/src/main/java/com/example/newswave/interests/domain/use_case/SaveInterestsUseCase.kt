package com.example.newswave.interests.domain.use_case

import com.example.newswave.interests.domain.repository.InterestsRepository
import javax.inject.Inject

class SaveInterestsUseCase @Inject constructor(
    private val interestsRepository: InterestsRepository
) {
    suspend operator fun invoke(interests: List<String>) =
        interestsRepository.saveInterests(interests)

}