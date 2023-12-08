package com.example.newswave.data.repositories

import com.example.newswave.data.datasource.local.NewsDao
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.utils.Resource
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRepository {
    suspend fun safeApiCall(apiToBeCalled: suspend () -> NewsDto): Resource<NewsDto> {

        return withContext(Dispatchers.IO) {
            try {
                val response  = apiToBeCalled()
                Resource.Success(response)
            } catch (e: HttpException) {
                Resource.Error(message = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                Resource.Error(message  = "Something went wrong")
            }
        }
    }


}