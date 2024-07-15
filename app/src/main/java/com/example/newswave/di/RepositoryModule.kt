package com.example.newswave.di

import com.example.newswave.bookmark.data.repository.BookmarkRepositoryImpl
import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.news.data.repository.NewsRepositoryImpl
import com.example.newswave.news.domain.repository.NewsRepository
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
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(bookmarkRepositoryImpl: BookmarkRepositoryImpl): BookmarkRepository




    @Binds
    @Singleton
    abstract fun bindSearchNewsRepository(searchNewsRepositoryImpl: SearchNewsRepositoryImpl): SearchNewsRepository


}

