package com.example.newswave.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "news")
data class News(
    val id:String,
    val title: String,
    val author: String?,
    val category: String,
    val timestamp: String,
    val imageUrl: String,
    @PrimaryKey
    val link: String,
    var isBookmarked:Boolean
)
