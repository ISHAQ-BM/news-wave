package com.example.newswave.di

import com.example.newswave.data.repositories.NewsRepositoryImpl
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.search.data.repository.SearchNewsRepositoryImpl
import com.example.newswave.search.domain.repository.SearchNewsRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl):NewsRepository

    @Binds
    @Singleton
    abstract fun bindNewssRepository(newsRepositoryImpl: com.example.newswave.news.data.repository.NewsRepositoryImpl):com.example.newswave.news.domain.repository.NewsRepository


    @Binds
    @Singleton
    abstract fun bindSearchNewsRepository(searchNewsRepositoryImpl: SearchNewsRepositoryImpl): SearchNewsRepository


}

