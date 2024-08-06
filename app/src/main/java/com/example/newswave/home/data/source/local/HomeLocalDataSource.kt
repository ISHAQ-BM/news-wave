package com.example.newswave.home.data.source.local

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newswave.core.data.source.remote.utils.Util
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.home.data.source.remote.NewsRemoteMediator
import com.example.newswave.home.data.source.remote.api.HomeApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomeLocalDataSource @Inject constructor(
    private val newsDatabase: NewsDatabase,
    private val homeApiService: HomeApiService
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
                    remoteMediator = NewsRemoteMediator(category,homeApiService, newsDatabase),
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