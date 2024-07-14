package com.example.newswave.search.domain.use_case

import com.example.newswave.search.domain.repository.SearchNewsRepository
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val searchNewsRepository: SearchNewsRepository
) {
    suspend operator fun invoke(searchQuery:String)=searchNewsRepository.searchNews(searchQuery)

}