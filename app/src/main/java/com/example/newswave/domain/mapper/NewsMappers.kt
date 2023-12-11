package com.example.newswave.domain.mapper

import com.example.newswave.data.datasource.network.models.ArticleDataDto
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.models.Article

fun List<ArticleDataDto>.toValidNews():List<ArticleDataDto> {
    return this.filter { !it.imageUrl.isNullOrEmpty() && !it.creator.isNullOrEmpty() }
}

fun NewsDto.toValidArticles():List<Article> {
    return results.toValidNews().map {
        Article(
            it.pubDate.split(" ")[1],
            it.title,
            it.link,
            it.creator?.get(0) ?: "",
            it.imageUrl ?: "",
            it.category[0],
            false,
        )
    }
}

