package com.example.newswave.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article (
    val pubTime:String,
    @PrimaryKey
    val title:String,
    val link:String,
    val creator:String,
    val imageUrl:String,
    val category:String,
    var isBookmarked:Boolean
)
