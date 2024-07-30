package com.example.newswave.interests.domain.use_case

import com.example.newswave.auth.domain.repository.AuthRepository
import com.example.newswave.interests.domain.repository.InterestsRepository
import javax.inject.Inject

class GetInterestsUseCase @Inject constructor (
    private val interestsRepository: InterestsRepository
){
    suspend operator fun invoke()=interestsRepository.getInterests()

}