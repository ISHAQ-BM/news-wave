package com.example.newswave.bookmark.domain.use_case

import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarkedNewsUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
){
    suspend operator fun invoke()=bookmarkRepository.getBookmarkedNews()

}