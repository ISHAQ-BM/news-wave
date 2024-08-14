package com.example.newswave.core.di


import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.core.domain.use_case.BookmarkNewsUseCase
import com.example.newswave.core.domain.use_case.UnBookmarkNewsUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideBookmarkNewsUseCase(bookmarkRepository: BookmarkRepository): BookmarkNewsUseCase {
        return BookmarkNewsUseCase(bookmarkRepository)
    }

    @Provides
    @Singleton
    fun provideUnBookmarkNewsUseCase(bookmarkRepository: BookmarkRepository): UnBookmarkNewsUseCase {
        return UnBookmarkNewsUseCase(bookmarkRepository)
    }


}
