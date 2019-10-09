package com.shubham.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.shubham.newsapp.data.db.NewsDao
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.data.network.NewsNetworkDataSource
import com.shubham.newsapp.data.network.response.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsNetworkDataSource: NewsNetworkDataSource
) : NewsRepository {

    init {
         newsNetworkDataSource
             .apply {
             downloadedNews.observeForever { newFetchedNews ->
                 persistFetchedCurrentWeather(newFetchedNews)
             }
         }
    }

    private fun persistFetchedCurrentWeather(fetchedNews: NewsResponse) {

//        fun deleteOldFetchedNews(){
//            newsDao.deleteOldNews()
//        }

        GlobalScope.launch(Dispatchers.IO) {

//            deleteOldFetchedNews()
            val newsList = fetchedNews.articles
            newsDao.upsert(newsList)
        }
    }

    override suspend fun getNews(): LiveData<List<Article>> {

        return withContext(Dispatchers.IO){
            
            getAllNews()
            return@withContext newsDao.getNews()
        }
    }

    private suspend fun getAllNews() {
        newsNetworkDataSource.fetchNews("india")
    }
}