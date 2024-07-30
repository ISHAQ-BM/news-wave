package com.example.newswave.di

import com.example.newswave.auth.data.repository.AuthRepositoryImpl
import com.example.newswave.auth.domain.repository.AuthRepository
import com.example.newswave.bookmark.data.repository.BookmarkRepositoryImpl
import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.home.data.repository.HomeRepositoryImpl
import com.example.newswave.home.domain.repository.HomeRepository
import com.example.newswave.interests.data.repository.InterestsRepositoryImpl
import com.example.newswave.interests.domain.repository.InterestsRepository
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
    abstract fun bindNewsRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(bookmarkRepositoryImpl: BookmarkRepositoryImpl): BookmarkRepository


    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository



    @Binds
    @Singleton
    abstract fun bindSearchNewsRepository(searchNewsRepositoryImpl: SearchNewsRepositoryImpl): SearchNewsRepository



    @Binds
    @Singleton
    abstract fun bindInterestsRepository(interestsRepositoryImpl: InterestsRepositoryImpl): InterestsRepository



}

