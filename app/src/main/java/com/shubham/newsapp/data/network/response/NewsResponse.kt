package com.shubham.newsapp.data.network.response

import com.shubham.newsapp.data.db.entity.Article


data class NewsResponse(

    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)