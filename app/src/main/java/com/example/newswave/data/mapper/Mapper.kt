package com.example.newswave.data.mapper

interface Mapper<in I,out O> {
    fun mapNewsDto(input : I ): O
}