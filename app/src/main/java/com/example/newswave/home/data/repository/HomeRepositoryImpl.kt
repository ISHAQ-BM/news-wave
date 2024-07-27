package com.example.newswave.home.data.repository

import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.home.data.source.remote.HomeRemoteDataSource
import com.example.newswave.core.domain.model.News
import com.example.newswave.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
): HomeRepository {
    override suspend fun getNewsHeadline(category:String): Flow<Result<List<News>, Error>> {
        return homeRemoteDataSource.getNewsHeadline(category)
    }
}