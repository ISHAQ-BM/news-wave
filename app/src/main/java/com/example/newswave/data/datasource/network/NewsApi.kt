package com.example.newswave.data.datasource.network

import com.example.newswave.data.datasource.network.models.NewsData
import com.example.newswave.utils.API_KEY
import com.example.newswave.utils.Resource
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    suspend fun getLatestNews(
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = "en"
    ): Resource<NewsData>


    @GET("news?")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = "en"
    ): Resource<NewsData>
}