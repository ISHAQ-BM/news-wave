package com.example.newswave.core.domain.use_case

import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.core.domain.model.News
import javax.inject.Inject

class BookmarkNewsUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
){
    suspend operator fun invoke(news: News)=bookmarkRepository.bookmarkNews(news)

}