package com.shubham.newsapp.data.network.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shubham.newsapp.data.network.NewsApiservice
import com.shubham.newsapp.data.network.response.NewsResponse
import com.shubham.newsapp.internal.NoConnectivityException

class NewsNetworkDataSourceImpl(
    private val newsApiservice: NewsApiservice
) :
    NewsNetworkDataSource {

    private val _downloadedNews = MutableLiveData<NewsResponse>()
    override val downloadedNews: LiveData<NewsResponse> = _downloadedNews

    override suspend fun fetchNews(query: String) {
        try {

            val fetchedNews = newsApiservice.getAllNews(query).await()
            _downloadedNews.postValue(fetchedNews)

        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }

    }
}