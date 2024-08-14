package com.example.newswave.core.domain.use_case

import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import javax.inject.Inject

class UnBookmarkNewsUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
){
    suspend operator fun invoke(title:String)=bookmarkRepository.unBookmarkNews(title)

}