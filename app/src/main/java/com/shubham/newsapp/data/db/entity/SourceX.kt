package com.shubham.newsapp.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_sources")
data class SourceX(

    @PrimaryKey(autoGenerate = true)
    val serial_id: Int? = null,
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)