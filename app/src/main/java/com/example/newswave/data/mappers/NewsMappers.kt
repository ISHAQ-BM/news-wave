package com.example.newswave.data.mappers

import com.example.newswave.data.datasource.network.models.News
import com.example.newswave.data.datasource.network.models.NewsData
import com.example.newswave.domain.models.Article

fun List<News>.toValidNews():List<News> {
    return this.filter { !it.imageUrl.isNullOrEmpty() && !it.creators.isNullOrEmpty() }
}

fun NewsData.toValidArticles():List<Article> {
    return news.toValidNews().map {
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