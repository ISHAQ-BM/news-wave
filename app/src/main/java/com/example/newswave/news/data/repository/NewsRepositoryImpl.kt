package com.example.newswave.news.data.repository

import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.news.data.source.remote.NewsRemoteDataSource
import com.example.newswave.core.domain.model.News
import com.example.newswave.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource
): NewsRepository {
    override suspend fun getNewsHeadline(category:String): Flow<Result<List<News>, Error>> {
        return newsRemoteDataSource.getNewsHeadline(category)
    }
}