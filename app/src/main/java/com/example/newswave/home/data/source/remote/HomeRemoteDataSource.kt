package com.example.newswave.home.data.source.remote


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newswave.core.data.source.remote.utils.Util
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.home.data.source.remote.api.NewsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val newsApiService: NewsApiService
) {
    suspend fun getNewsHeadline(category: String): Flow<Result<PagingData<News>, Error.Network>> {
        return flow {
            try {
                Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = { NewsPagingSource(category, newsApiService) }
                ).flow
                    .map<PagingData<News>, Result<PagingData<News>, Error.Network>> { pagingData ->
                        Result.Success(
                            pagingData
                        )
                    }
                    .collect { result ->
                        emit(result)
                    }
            } catch (e: HttpException) {
                val error = Util.getErrorFromStatusCode(e.code())
                emit(Result.Error(error))
            } catch (e: IOException) {
                emit(Result.Error(Error.Network.NO_INTERNET))
            } catch (e: Exception) {
                emit(Result.Error(Error.Network.UNKNOWN))
            }
        }
    }
}