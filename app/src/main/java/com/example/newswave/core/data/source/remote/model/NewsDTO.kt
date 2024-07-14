package com.example.newswave.core.data.source.remote.model


import com.squareup.moshi.Json

data class NewsDTO(
    @Json(name = "nextPage")
    val nextPage: String,
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "status")
    val status: String,
    @Json(name = "totalResults")
    val totalResults: Int
)