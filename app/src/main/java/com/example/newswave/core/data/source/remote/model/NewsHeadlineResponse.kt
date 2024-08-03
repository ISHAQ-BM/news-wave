package com.example.newswave.core.data.source.remote.model


import com.squareup.moshi.Json

data class NewsHeadlineResponse(
    @Json(name = "nextPage")
    val nextPage: String,
    @Json(name = "results")
    val results: List<NewsDTO>,
    @Json(name = "status")
    val status: String,
    @Json(name = "totalResults")
    val totalResults: Int
)