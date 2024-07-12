package com.example.newswave.news.domain.model



data class News(
    val id:String,
    val title: String,
    val author: String?,
    val category: String,
    val timestamp: String,
    val imageUrl: String,
    val link: String,
)
