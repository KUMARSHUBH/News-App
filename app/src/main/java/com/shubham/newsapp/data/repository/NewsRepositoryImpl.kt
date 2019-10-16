package com.shubham.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.shubham.newsapp.data.db.dao.NewsDao
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.data.network.dataSource.NewsNetworkDataSource
import com.shubham.newsapp.data.network.response.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsNetworkDataSource: NewsNetworkDataSource
) : NewsRepository {

    override suspend fun getNewsFromSource(domain: String): LiveData<List<Article>> {
        return withContext(Dispatchers.IO){

            getAllNewsFromSources(domain)
            return@withContext newsDao.getNews()
        }
    }


    init {
        newsNetworkDataSource
            .apply {
                downloadedNews.observeForever { newFetchedNews ->
                    persistFetchedNews(newFetchedNews)
                }
            }
    }

    private fun persistFetchedNews(fetchedNews: NewsResponse) {

        fun deleteOldFetchedNews(){
            newsDao.deleteOldNews()
        }

        GlobalScope.launch(Dispatchers.IO) {

            deleteOldFetchedNews()
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

    private suspend fun getAllNewsFromSources(domain: String)
    {
        newsNetworkDataSource.fetchNewsFromSource(domain)
    }
}