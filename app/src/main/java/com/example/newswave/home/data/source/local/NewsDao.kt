package com.example.newswave.home.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.newswave.core.domain.model.News
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {


    @Query("SELECT * FROM news WHERE category = :category")
    fun newsByCategory(category:String): PagingSource<Int, News>


    @Upsert
    suspend fun upsertAll(news: List<News>)


    @Query("DELETE  FROM news")
    suspend fun clearAll()

}