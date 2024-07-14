package com.example.newswave.news.data.source.remote


import android.util.Log
import com.example.newswave.core.data.source.remote.utils.Util
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.news.data.source.remote.api.NewsApiService
import com.example.newswave.core.domain.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsApiService: NewsApiService
) {

    suspend fun getNewsHeadline(category:String):Flow<Result<List<News>,Error.Network>> {
        return flow {
            try {
                val response = newsApiService.getNewsHeadline(category = category)
                Log.d("response","${response}")
                if (response.isSuccessful) {
                    Log.d("response body","${response.body()!!.results}")
                    val newsList = response.body()!!.results.map { News(
                        it.link,
                        it.title,
                        it.creator?.getOrNull(0),
                        it.category[0],
                        it.pubDate,
                        it.imageUrl ?:"https://media.gettyimages.com/id/1311148884/vector/abstract-globe-background.jpg?s=612x612&w=0&k=20&c=9rVQfrUGNtR5Q0ygmuQ9jviVUfrnYHUHcfiwaH5-WFE=",
                        it.link
                    )
                    }
                    emit(Result.Success(newsList))
                } else {
                    val error = Util.getErrorFromStatusCode(response.code())
                    emit(Result.Error(error))
                }
            } catch (e: HttpException) {
                val error = Util.getErrorFromStatusCode(e.code())
                emit(Result.Error(error))
            } catch (e: IOException) {
                emit(Result.Error(Error.Network.NO_INTERNET))
            } catch (e: Exception) {
                Log.d("response exceptione","${e.message}")
                Log.d("response exceptione","${e.printStackTrace()}")
                Log.d("response exceptione","${e.localizedMessage}")
                emit(Result.Error(Error.Network.UNKNOWN))
            }
        }
    }
}
