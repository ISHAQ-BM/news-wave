package com.example.newswave.home.domain.use_case

import com.example.newswave.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetNewsHeadlinesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke(category:String)=homeRepository.getNewsHeadline(category = category)

}