package com.example.newswave.bookmark.data.repository

import com.example.newswave.bookmark.data.source.local.BookmarkLocalDataSource
import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkLocalDataSource: BookmarkLocalDataSource
) : BookmarkRepository {
    override suspend fun getBookmarkedNews(): Flow<Result<List<News>, Error>> {
        return bookmarkLocalDataSource.getBookmarkedNews()
    }

    override suspend fun bookmarkNews(news:News) = bookmarkLocalDataSource.bookmarkNews(news)

    override suspend fun unBookmarkNews(news:News) = bookmarkLocalDataSource.unBookmarkNews(news)
}