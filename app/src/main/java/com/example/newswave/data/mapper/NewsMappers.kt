package com.example.newswave.data.mapper

import android.annotation.SuppressLint
import android.util.Log
import com.example.newswave.data.datasource.network.models.ArticleDataDto
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.models.Article
import java.text.SimpleDateFormat

fun List<ArticleDataDto>.toValidNews():List<ArticleDataDto> {
    return this.filter { !it.imageUrl.isNullOrEmpty() && !it.creator.isNullOrEmpty() }.toSet().toList()
}

fun NewsDto.toValidArticles():MutableList<Article> {
    return results.toValidNews().map {
        Article(
            formatPublishedDate(it.pubDate),
            it.title,
            it.link,
            it.creator?.get(0) ?: "",
            it.imageUrl ?: "",
            it.category[0],
            false,
        )
    }.toMutableList()
}

@SuppressLint("SimpleDateFormat")
fun formatPublishedDate(date:String):String{
    Log.d("news time", date)
    val currentTimeMillis = System.currentTimeMillis()
    val publishedAt= SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date)?.time

    val timeAgo= publishedAt?.let { (currentTimeMillis - it)/60000 }!!

    return if (timeAgo < 60)
        "${timeAgo}m"
    else
        "${timeAgo / 60}h"

}

