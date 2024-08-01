package com.example.newswave.bookmark.domain.repository

import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun getBookmarkedNews():Flow<Result<List<News>,Error>>
    suspend fun bookmarkNews(news: News):Flow<Result<Boolean,Error>>
    suspend fun unBookmarkNews(title:String):Flow<Result<Boolean,Error>>
}