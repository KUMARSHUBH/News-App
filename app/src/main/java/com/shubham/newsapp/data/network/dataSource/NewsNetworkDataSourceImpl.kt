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


    override suspend fun fetchSearchNews(keyword: String) {

        try {
            val fetchedNews = newsApiservice.getNewsSearch(keyword)
                .await()
            _downloadedNews.postValue(fetchedNews)

        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }
    }


    override suspend fun fetchTopHeadlinesCategory(country: String, category: String) {

        try {
            val fetchedNews = newsApiservice.getTopCategoryRelatedNews(country,category)
                .await()
            _downloadedNews.postValue(fetchedNews)

        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }
    }

    override suspend fun fetchEverything(domains: String) {

        try {

            val fetchedNews = newsApiservice.getAllTheNews(domains).await()
            _downloadedNews.postValue(fetchedNews)

        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }
    }

    override suspend fun fetchTopNews(language: String) {

        try {
            val fetchedNews = newsApiservice.getTopNews(language).await()
            _downloadedNews.postValue(fetchedNews)

        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }

    }


    private val _downloadedNews = MutableLiveData<NewsResponse>()
    override val downloadedNews: LiveData<NewsResponse> = _downloadedNews

    override suspend fun fetchNewsFromSource(domains: String) {

        try {

            val fetchedNews = newsApiservice.getEverything(domains).await()
            _downloadedNews.postValue(fetchedNews)

        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }

    }


    override suspend fun fetchNews(query: String) {
        try {

            val fetchedNews = newsApiservice.getAllNews(query).await()
            _downloadedNews.postValue(fetchedNews)

        }catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection", e)
        }

    }
}