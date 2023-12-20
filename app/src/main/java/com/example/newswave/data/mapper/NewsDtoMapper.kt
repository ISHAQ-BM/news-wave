package com.example.newswave.data.mapper

import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.models.News

class NewsDtoMapper(): Mapper<NewsDto, News> {
    override fun mapNewsDto(input: NewsDto): News {
        return News(
            input.toValidArticles().toMutableList(),
            input.nextPage
        )
    }

}