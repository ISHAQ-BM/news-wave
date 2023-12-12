package com.example.newswave.data.repositories

import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.data.mapper.NewsDtoMapper
import com.example.newswave.domain.models.News
import com.example.newswave.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

abstract class BaseRepository (
    private val newsDtoMapper:NewsDtoMapper
){
    suspend fun safeApiCall(apiToBeCalled: suspend () -> NewsDto): Resource<News> {

        return withContext(Dispatchers.IO) {
            try {
                val response  = apiToBeCalled()

                Resource.Success(newsDtoMapper.mapNewsDto(response))
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