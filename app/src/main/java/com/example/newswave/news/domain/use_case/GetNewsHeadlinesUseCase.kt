package com.example.newswave.news.domain.use_case

import com.example.newswave.news.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(category:String)=newsRepository.getNewsHeadline(category = category)

}