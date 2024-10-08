package com.example.newswave.home.domain.repository

import androidx.paging.PagingData
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
  suspend fun getNewsHeadline(category:String): Flow<Result<PagingData<News>, Error>>
}