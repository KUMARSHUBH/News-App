package com.shubham.newsapp.data.network.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shubham.newsapp.data.network.NewsApiservice
import com.shubham.newsapp.data.network.response.NewsSourcesResponse
import com.shubham.newsapp.internal.NoConnectivityException

class NewsSourcesNetworkDataSourceImpl(
    private val newsApiservice: NewsApiservice
) : NewsSourcesNetworkDataSource {



    override suspend fun fetchNewsSources() {

        try {
            val fetchedNewsSources = newsApiservice.getSources()
                .await()
            _downloadedNewsSources.postValue(fetchedNewsSources)
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    private val _downloadedNewsSources = MutableLiveData<NewsSourcesResponse>()

    override val downloadedNewsSources: LiveData<NewsSourcesResponse>
        get() = _downloadedNewsSources



}