package com.shubham.newsapp.data.network.dataSource

import androidx.lifecycle.LiveData
import com.shubham.newsapp.data.network.response.NewsResponse

interface NewsNetworkDataSource{

    val downloadedNews: LiveData<NewsResponse>

    suspend fun fetchNews(query: String)
    suspend fun fetchNewsFromSource(domains: String)
    suspend fun fetchTopNews(language: String)
}