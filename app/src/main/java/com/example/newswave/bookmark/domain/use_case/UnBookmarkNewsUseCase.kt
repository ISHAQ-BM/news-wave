package com.example.newswave.bookmark.domain.use_case

import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.core.domain.model.News
import javax.inject.Inject

class UnBookmarkNewsUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
){
    suspend operator fun invoke(news: News)=bookmarkRepository.unBookmarkNews(news)

}