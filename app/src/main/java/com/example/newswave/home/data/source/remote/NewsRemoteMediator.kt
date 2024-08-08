package com.example.newswave.home.data.source.remote

import androidx.compose.runtime.key
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.PrimaryKey
import androidx.room.withTransaction
import com.example.newswave.core.domain.model.News
import com.example.newswave.home.data.source.local.NewsDao
import com.example.newswave.home.data.source.local.NewsDatabase
import com.example.newswave.home.data.source.remote.api.HomeApiService
import retrofit2.HttpException
import java.io.IOException

private val keys = mutableMapOf<String, String?>()

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val category: String,
    private val homeApiService: HomeApiService,
    private val newsDatabase: NewsDatabase
): RemoteMediator<Int, News>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, News>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    keys[category]
                }
            }

            val response = if (loadKey==null){
                homeApiService.getNewsHeadline(category = category)
            }else{
                homeApiService.getNewsHeadlinePerPage(category = category, page = loadKey)
            }


            val newsItems = response.body()!!.results.map {
                News(
                    it.link,
                    it.title,
                    it.creator?.getOrNull(0),
                    it.category[0],
                    it.pubDate,
                    it.imageUrl ?:"https://media.gettyimages.com/id/1311148884/vector/abstract-globe-background.jpg?s=612x612&w=0&k=20&c=9rVQfrUGNtR5Q0ygmuQ9jviVUfrnYHUHcfiwaH5-WFE=",
                    it.link,
                    false
                )
            }

            keys[category]= response.body()!!.nextPage
            val endOfPaginationReached = newsItems.isEmpty()

            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    newsDatabase.newsDao().clearAll()
                }
                newsDatabase.newsDao().upsertAll(newsItems)
            }



            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached,
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


}