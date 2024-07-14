package com.example.newswave.news.data.source.remote.api


import com.example.newswave.BuildConfig.API_KEY
import com.example.newswave.core.data.source.remote.model.NewsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("news?")
    suspend fun getNewsHeadline(
        @Query("apikey")
        apikey: String = API_KEY,
        @Query("language")
        language: String = "en",
        @Query("category")
        category:String
    ): Response<NewsDTO>
}