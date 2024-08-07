package com.example.newswave.search.data.source.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newswave.core.data.source.remote.utils.Util
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.search.data.source.remote.api.SearchApiService

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchNewsRemoteDataSource @Inject constructor(
    private val searchApiService: SearchApiService
) {
    suspend fun searchNews(searchQuery:String): Flow<Result<PagingData<News>, Error.Network>> {
        return flow {
            try {
                Pager(
                    config = PagingConfig(
                        pageSize = 10,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = { SearchPagingSource(searchQuery, searchApiService) }
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

    suspend fun getLatestNews():Flow<Result<List<News>, Error.Network>>{
        return flow {
            try {
               val response=searchApiService.getLatestNews()
                if (response.isSuccessful){
                    Log.d("response hhh","${response.body()!!.results}")
                    val latestNews = response.body()!!.results.map { News(
                        it.link,
                        it.title,
                        it.creator?.getOrNull(0),
                        it.category[0],
                        it.pubDate,
                        it.imageUrl ?:"",
                        it.link,
                        false
                    )
                    }
                    emit(Result.Success(latestNews))
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