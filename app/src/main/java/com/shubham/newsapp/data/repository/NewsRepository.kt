package com.shubham.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.shubham.newsapp.data.db.entity.Article

interface NewsRepository {

    suspend fun getNews() : LiveData<List<Article>>

    suspend fun getNewsFromSource(domains: String): LiveData<List<Article>>

    suspend fun getTopNews(language: String): LiveData<List<Article>>
}