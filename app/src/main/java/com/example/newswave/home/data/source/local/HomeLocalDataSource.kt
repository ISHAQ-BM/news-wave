package com.example.newswave.home.data.source.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.home.data.source.remote.HomeRemoteMediator
import com.example.newswave.home.data.source.remote.api.NewsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class HomeLocalDataSource @Inject constructor(
    private val newsDatabase: NewsDatabase,
    private val newsApiService: NewsApiService
) {
    @OptIn(ExperimentalPagingApi::class)
    suspend fun getNewsHeadline(category: String): Flow<Result<PagingData<News>, Error>> {
        return flow {
            try {
                Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = false
                    ),
                    remoteMediator = HomeRemoteMediator(category, newsApiService, newsDatabase),
                    pagingSourceFactory = { newsDatabase.newsDao().newsByCategory(category) }
                ).flow
                    .map<PagingData<News>, Result<PagingData<News>, Error.Local>> { pagingData ->
                        Result.Success(
                            pagingData
                        )
                    }
                    .collect { result ->
                        emit(result)
                    }
            } catch (e: IOException) {
                emit(Result.Error(Error.Local.DISK_FULL))
            } catch (e: Exception) {
                emit(Result.Error(Error.Local.UNKNOWN))
            }
        }
    }





}