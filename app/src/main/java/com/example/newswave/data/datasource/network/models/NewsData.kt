package com.example.newswave.data.datasource.network.models

import com.squareup.moshi.Json

data class NewsData (
    @field:Json(name = "results")
    val news:List<News>
)
