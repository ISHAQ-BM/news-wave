package com.example.newswave.data.datasource.network.models

import com.squareup.moshi.Json

data class ArticleDataDto(
    @field:Json(name ="category")
    val category: List<String>,
    @field:Json(name ="creator")
    val creator: List<String>?,
    @field:Json(name ="image_url")
    val imageUrl: String?,
    @field:Json(name ="link")
    val link: String,
    @field:Json(name ="pubDate")
    val pubDate: String,
    @field:Json(name ="title")
    val title: String,

)
