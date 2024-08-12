package com.example.newswave.home.di

import android.content.Context
import androidx.room.Room
import com.example.newswave.BuildConfig
import com.example.newswave.home.data.repository.HomeRepositoryImpl
import com.example.newswave.home.data.source.local.HomeLocalDataSource
import com.example.newswave.home.data.source.local.NewsDao
import com.example.newswave.home.data.source.local.NewsDatabase
import com.example.newswave.home.data.source.remote.HomeRemoteDataSource
import com.example.newswave.home.data.source.remote.api.NewsApiService
import com.example.newswave.home.domain.repository.HomeRepository
import com.example.newswave.home.domain.use_case.GetNewsHeadlinesUseCase
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
object HomeModule {

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
    fun provideGetNewsHeadlinesUseCase(homeRepository: HomeRepository): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(homeRepository)
    }

    @Provides
    @Singleton
    fun provideHomeRemoteDataSource(newsApiService: NewsApiService): HomeRemoteDataSource {
        return HomeRemoteDataSource(newsApiService)
    }


    @Provides
    @Singleton
    fun provideHomeLocalDataSource(
        newsDatabase: NewsDatabase,
        newsApiService: NewsApiService
    ): HomeLocalDataSource {
        return HomeLocalDataSource(newsDatabase, newsApiService)
    }


}

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository


}