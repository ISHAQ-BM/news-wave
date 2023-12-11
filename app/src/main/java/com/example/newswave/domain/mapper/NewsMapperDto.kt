package com.example.newswave.domain.mapper

import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.models.News

class NewsMapperDto ():Mapper<NewsDto,News>{
    override fun map(input: NewsDto): News {
        return News(
            input.toValidArticles(),
            input.nextPage
        )
    }

}