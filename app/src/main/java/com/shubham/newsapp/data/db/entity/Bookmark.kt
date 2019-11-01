package com.shubham.newsapp.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "news_bookmark")
@Fts4
data class Bookmark(

    @PrimaryKey(autoGenerate = true)
    val rowid: Int? = null,

    @Embedded
    var source: Source?,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?
)