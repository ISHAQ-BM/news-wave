package com.example.newswave.search.domain.use_case

import com.example.newswave.search.domain.repository.SearchNewsRepository
import javax.inject.Inject

class GetLatestNewsUseCase @Inject constructor(
    private val searchNewsRepository: SearchNewsRepository
) {
    suspend operator fun invoke()=searchNewsRepository.getLatestNews()

}