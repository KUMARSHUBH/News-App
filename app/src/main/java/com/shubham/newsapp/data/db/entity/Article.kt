package com.shubham.newsapp.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class Article(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @Embedded
    val source: Source?,

    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)