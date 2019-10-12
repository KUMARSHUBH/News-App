package com.shubham.newsapp.data.network.dataSource

import androidx.lifecycle.LiveData
import com.shubham.newsapp.data.network.response.NewsSourcesResponse

interface NewsSourcesNetworkDataSource {

    val downloadedNewsSources : LiveData<NewsSourcesResponse>

    suspend fun fetchNewsSources()
}