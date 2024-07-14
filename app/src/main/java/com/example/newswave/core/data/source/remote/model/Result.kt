package com.example.newswave.core.data.source.remote.model


import com.squareup.moshi.Json

data class Result(
    @Json(name = "article_id")
    val articleId: String,
    @Json(name = "category")
    val category: List<String>,
    @Json(name = "content")
    val content: String,
    @Json(name = "country")
    val country: List<String>,
    @Json(name = "creator")
    val creator: List<String>?,
    @Json(name = "description")
    val description: String,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "keywords")
    val keywords: List<String>,
    @Json(name = "language")
    val language: String,
    @Json(name = "link")
    val link: String,
    @Json(name = "pubDate")
    val pubDate: String,
    @Json(name = "title")
    val title: String,
)