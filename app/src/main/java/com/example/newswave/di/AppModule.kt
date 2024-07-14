package com.example.newswave.di


import android.content.Context
import androidx.room.Room
import com.example.newswave.BuildConfig
import com.example.newswave.data.datasource.local.NewsDao
import com.example.newswave.data.datasource.local.NewsDatabase
import com.example.newswave.data.datasource.network.NewsApi
import com.example.newswave.data.mapper.NewsDtoMapper
import com.example.newswave.news.data.source.remote.NewsRemoteDataSource
import com.example.newswave.news.data.source.remote.api.NewsApiService
import com.example.newswave.news.domain.repository.NewsRepository
import com.example.newswave.news.domain.use_case.GetNewsHeadlinesUseCase
import com.example.newswave.search.data.source.remote.SearchNewsRemoteDataSource
import com.example.newswave.search.data.source.remote.api.SearchApiService
import com.example.newswave.search.domain.repository.SearchNewsRepository
import com.example.newswave.search.domain.use_case.SearchNewsUseCase
import dagger.Binds

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
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

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
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDtoMapper():NewsDtoMapper{
        return NewsDtoMapper()
    }

    @Provides
    @Singleton
    fun provideGetNewsHeadlinesUseCase(newsRepository: NewsRepository):GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(newsRepository)
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
    fun provideSearchNewsRemoteDataSource(searchApiService: SearchApiService):SearchNewsRemoteDataSource{
        return SearchNewsRemoteDataSource(searchApiService)
    }


}
