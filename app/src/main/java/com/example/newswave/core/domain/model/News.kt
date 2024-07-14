package com.example.newswave.core.domain.model



data class News(
    val id:String,
    val title: String,
    val author: String?,
    val category: String,
    val timestamp: String,
    val imageUrl: String,
    val link: String,
)
