package com.example.newswave.news.domain.repository

import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
  suspend fun getNewsHeadline(category:String): Flow<Result<List<News>, Error>>
}