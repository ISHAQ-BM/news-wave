package com.example.newswave.home.data.repository

import androidx.paging.PagingData
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import com.example.newswave.home.data.source.remote.HomeRemoteDataSource
import com.example.newswave.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    //private val homeLocalDataSource: HomeLocalDataSource,
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override suspend fun getNewsHeadline(category:String): Flow<Result<PagingData<News>, Error>> {
        return homeRemoteDataSource.getNewsHeadline(category)
    }
}