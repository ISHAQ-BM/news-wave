package com.example.newswave.bookmark.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.newswave.bookmark.data.source.remote.BookmarkRemoteDataSource
import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkRemoteDataSource: BookmarkRemoteDataSource
) : BookmarkRepository {
    override suspend fun getBookmarkedNews(): Flow<Result<List<News>, Error>> =
        bookmarkRemoteDataSource.getBookmarkedNews()

    override suspend fun bookmarkNews(news: News) = bookmarkRemoteDataSource.bookmarkNews(news)
    override suspend fun unBookmarkNews(title: String) =
        bookmarkRemoteDataSource.unBookmarkNews(title)
}