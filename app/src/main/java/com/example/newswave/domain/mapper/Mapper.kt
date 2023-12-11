package com.example.newswave.domain.mapper

interface Mapper<in I,out O> {
    fun map(input : I ): O
}