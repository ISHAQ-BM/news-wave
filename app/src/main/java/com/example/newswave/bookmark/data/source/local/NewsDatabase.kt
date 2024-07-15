package com.example.newswave.bookmark.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newswave.core.domain.model.News

@Database(entities = [News::class], version = 2, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDao
}