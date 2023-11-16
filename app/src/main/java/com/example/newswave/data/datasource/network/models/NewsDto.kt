package com.example.newswave.data.datasource.network.models

import com.squareup.moshi.Json

data class NewsDto (
    @field:Json(name = "results")
    val articles:List<NewsDataDto>
)
