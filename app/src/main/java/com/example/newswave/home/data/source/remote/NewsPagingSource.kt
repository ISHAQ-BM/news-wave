package com.example.newswave.home.data.source.remote

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newswave.core.domain.model.News
import com.example.newswave.home.data.source.remote.api.NewsApiService
import okio.IOException

class NewsPagingSource (
    val category:String,
    private val newsApiService: NewsApiService
): PagingSource<String, News>() {
    override fun getRefreshKey(state: PagingState<String, News>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<String>): LoadResult<String, News> {
        return try {
            val pageKey=params.key
            val response = if (pageKey==null){
                newsApiService.getNewsHeadline(category = category)
            }else{
                newsApiService.getNewsHeadlinePerPage(category = category, page = pageKey)
            }


            val news=response.body()?.results?.map { News(
                        it.link,
                        it.title,
                       it.creator?.getOrNull(0),
                       it.category[0],
                        it.pubDate,
                        it.imageUrl ?:"https://media.gettyimages.com/id/1311148884/vector/abstract-globe-background.jpg?s=612x612&w=0&k=20&c=9rVQfrUGNtR5Q0ygmuQ9jviVUfrnYHUHcfiwaH5-WFE=",
                        it.link,
                        false
                    ) }

            val nextKey=response.body()?.nextPage
            LoadResult.Page(
                data = news?: listOf(),
                nextKey = nextKey,
                prevKey = null
            )

        }catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}