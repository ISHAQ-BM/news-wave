package com.example.newswave.bookmark.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newswave.core.domain.model.News
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsDao {


    @Query("SELECT * FROM news")
    fun selectAll(): Flow<List<News>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News)


    @Delete
    fun delete(news: News)

}