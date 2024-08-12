package com.example.newswave.home.data.source.remote.api


import com.example.newswave.BuildConfig.API_KEY
import com.example.newswave.core.data.source.remote.model.NewsHeadlineResponse
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
    ): Response<NewsHeadlineResponse>

    @GET("news?")
    suspend fun getNewsHeadlinePerPage(
        @Query("apikey")
        apikey: String = API_KEY,
        @Query("language")
        language: String = "en",
        @Query("category")
        category:String,
        @Query("page")
        page:String
    ): Response<NewsHeadlineResponse>
}