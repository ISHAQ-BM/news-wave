package com.example.newswave.domain.models

data class News (
    val articles:List<Article>,
    val nextPage:String
)