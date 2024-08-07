package com.example.newswave.core.data.source.remote.model


import com.squareup.moshi.Json

data class NewsDTO(
    @field:Json(name = "article_id")
    val articleId: String,
    @field:Json(name = "category")
    val category: List<String>,
    @Json(name = "creator")
    val creator: List<String>?,
    @field:Json(name = "image_url")
    val imageUrl: String?,
    @field:Json(name = "link")
    val link: String,
    @field:Json(name = "pubDate")
    val pubDate: String,
    @field:Json(name = "title")
    val title: String,
)