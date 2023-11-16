package com.example.newswave.data.datasource.network

import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.utils.API_KEY
import com.example.newswave.domain.utils.Resource
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    suspend fun getLatestNews(
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = "en",
        @Query("timeframe")
        timeframe:String="24",
        @Query("image")
        image:String="1",
        @Query("timezone")
        timeZone:String="America/New_york",
        @Query("page")
        pageNumber :Int= 1
    ): NewsDto


    @GET("news?")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("language")
        language: String = "en",
        @Query("timeframe")
        timeframe:String="24",
        @Query("timezone")
        timeZone:String="America/New_york",
        @Query("page")
        pageNumber :Int= 1
    ): NewsDto
}