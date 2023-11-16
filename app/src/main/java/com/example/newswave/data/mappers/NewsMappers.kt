package com.example.newswave.data.mappers

import com.example.newswave.data.datasource.network.models.NewsDataDto
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.models.Article

fun List<NewsDataDto>.toValidNews():List<NewsDataDto> {
    return this.filter { !it.imageUrl.isNullOrEmpty() && !it.creators.isNullOrEmpty() }
}

fun NewsDto.toValidArticles():List<Article> {
    return articles.toValidNews().map {
        Article(
            it.pubDate.split(" ")[1],
            it.title,
            it.link,
            it.creators?.get(0) ?: "",
            it.imageUrl ?: "",
            it.category[0],
            false,
        )
    }
}

fun List<Article>.getCategories():List<String>{
    return this.map { it.category }
}