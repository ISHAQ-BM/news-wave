package com.example.newswave.di


import android.content.Context
import androidx.room.Room
import com.example.newswave.BuildConfig
import com.example.newswave.bookmark.data.source.local.BookmarkLocalDataSource
import com.example.newswave.bookmark.data.source.local.NewsDao
import com.example.newswave.bookmark.data.source.local.NewsDatabase
import com.example.newswave.bookmark.domain.repository.BookmarkRepository
import com.example.newswave.bookmark.domain.use_case.GetBookmarkedNewsUseCase
import com.example.newswave.news.data.source.remote.NewsRemoteDataSource
import com.example.newswave.news.data.source.remote.api.NewsApiService
import com.example.newswave.news.domain.repository.NewsRepository
import com.example.newswave.news.domain.use_case.GetNewsHeadlinesUseCase
import com.example.newswave.search.data.source.remote.SearchNewsRemoteDataSource
import com.example.newswave.search.data.source.remote.api.SearchApiService
import com.example.newswave.search.domain.repository.SearchNewsRepository
import com.example.newswave.search.domain.use_case.SearchNewsUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {



    @Provides
    @Singleton
    fun provideNewsApiService(): NewsApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchApiService(): SearchApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SearchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao()
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_database"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }



    @Provides
    @Singleton
    fun provideGetNewsHeadlinesUseCase(newsRepository: NewsRepository):GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideGetBookmarkedNewsUseCase(bookmarkRepository: BookmarkRepository):GetBookmarkedNewsUseCase{
        return GetBookmarkedNewsUseCase(bookmarkRepository)
    }

    @Provides
    @Singleton
    fun provideSearchNewsUseCase(searchNewsRepository: SearchNewsRepository):SearchNewsUseCase{
        return SearchNewsUseCase(searchNewsRepository)
    }

    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(newsApiService: NewsApiService):NewsRemoteDataSource{
        return NewsRemoteDataSource(newsApiService)
    }

    @Provides
    @Singleton
    fun provideBookmarkLocalDataSource(newsDao: NewsDao):BookmarkLocalDataSource{
        return BookmarkLocalDataSource(newsDao)
    }

    @Provides
    @Singleton
    fun provideSearchNewsRemoteDataSource(searchApiService: SearchApiService):SearchNewsRemoteDataSource{
        return SearchNewsRemoteDataSource(searchApiService)
    }


}
