package com.example.newswave.search.di

import com.example.newswave.BuildConfig
import com.example.newswave.search.data.repository.SearchNewsRepositoryImpl
import com.example.newswave.search.data.source.remote.SearchNewsRemoteDataSource
import com.example.newswave.search.data.source.remote.api.SearchApiService
import com.example.newswave.search.domain.repository.SearchNewsRepository
import com.example.newswave.search.domain.use_case.GetLatestNewsUseCase
import com.example.newswave.search.domain.use_case.SearchNewsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
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
    fun provideSearchNewsUseCase(searchNewsRepository: SearchNewsRepository): SearchNewsUseCase {
        return SearchNewsUseCase(searchNewsRepository)
    }

    @Provides
    @Singleton
    fun provideGetLatestNewsUseCase(searchNewsRepository: SearchNewsRepository): GetLatestNewsUseCase {
        return GetLatestNewsUseCase(searchNewsRepository)
    }

    @Provides
    @Singleton
    fun provideSearchNewsRemoteDataSource(searchApiService: SearchApiService): SearchNewsRemoteDataSource {
        return SearchNewsRemoteDataSource(searchApiService)
    }


}

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSearchNewsRepository(searchNewsRepositoryImpl: SearchNewsRepositoryImpl): SearchNewsRepository


}