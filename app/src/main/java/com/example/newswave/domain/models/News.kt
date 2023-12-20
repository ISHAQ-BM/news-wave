package com.example.newswave.domain.models

data class News (
    val articles:MutableList<Article>,
    var nextPage:String?
)