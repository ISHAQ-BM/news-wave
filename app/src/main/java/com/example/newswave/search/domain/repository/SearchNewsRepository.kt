package com.example.newswave.search.domain.repository

import androidx.paging.PagingData
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface SearchNewsRepository {
    suspend fun searchNews(searchQuery:String): Flow<Result<PagingData<News>, Error>>
}