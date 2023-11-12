package com.example.newswave.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newswave.domain.models.Article


@Dao
interface NewsDao {

    //  implement a method to retrieve all articles from the database
    @Query("SELECT * FROM news")
    fun getNews(): LiveData<List<Article>>

    //  implement a method to insert a Article into the database
    //  (use OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    //  implement a method to delete a Articles from the database.
    @Delete
    fun delete(article: Article)
}