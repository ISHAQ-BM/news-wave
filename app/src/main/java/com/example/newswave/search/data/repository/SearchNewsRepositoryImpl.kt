package com.example.newswave.search.data.repository

import androidx.paging.PagingData
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.search.data.source.remote.SearchNewsRemoteDataSource
import com.example.newswave.search.domain.repository.SearchNewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNewsRepositoryImpl @Inject constructor(
    private val searchNewsRemoteDataSource: SearchNewsRemoteDataSource
):SearchNewsRepository {
    override suspend fun searchNews(searchQuery: String): Flow<Result<PagingData<News>, Error>> {
        return searchNewsRemoteDataSource.searchNews(searchQuery)
    }

    override suspend fun getLatestNews(): Flow<Result<List<News>, Error>> {
        return searchNewsRemoteDataSource.getLatestNews()
    }

}