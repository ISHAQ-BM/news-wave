package com.example.newswave.data.datasource.network.models


import com.squareup.moshi.Json

data class NewsDto(
    @field:Json(name = "nextPage")
    val nextPage: String,
    @field:Json(name = "results")
    val results: List<NewsDataDto>

)