package com.example.newswave.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newswave.domain.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDao
}