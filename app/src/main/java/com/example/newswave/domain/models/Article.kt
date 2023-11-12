package com.example.newswave.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class Article (
    val pubTime:String,
    @PrimaryKey
    val title:String,
    val link:String,
    val creator:String,
    val imageUrl:String,
    val category:List<String>,
    var isSaved:Boolean
)
