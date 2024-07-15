package com.example.newswave.bookmark.data.source.local

import android.util.Log
import com.example.newswave.core.data.source.remote.utils.Util
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Error
import com.example.newswave.core.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class BookmarkLocalDataSource @Inject constructor(
    private val newsDao: NewsDao
) {
    fun getBookmarkedNews(): Flow<Result<List<News>, Error.Local>> {
        return flow {
            try {
                newsDao.selectAll().flowOn(Dispatchers.IO).collect{
                    emit(Result.Success(it))
                }

            } catch (e: IOException) {
                emit(Result.Error(Error.Local.DISK_FULL))
            } catch (e: Exception) {
                Log.d("bookmark",e.message.toString())
                emit(Result.Error(Error.Local.UNKNOWN))
            }
        }

    }

    suspend fun bookmarkNews(news:News) {
        newsDao.insert(news)
    }

    suspend fun unBookmarkNews(news: News) {
        newsDao.delete(news)
    }
}