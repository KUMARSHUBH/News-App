package com.shubham.newsapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.shubham.newsapp.data.db.dao.NewsDao
import com.shubham.newsapp.data.db.dao.NewsSourcesDao
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.data.network.dataSource.NewsNetworkDataSource
import com.shubham.newsapp.data.network.response.NewsResponse
import com.shubham.newsapp.internal.getHostName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsNetworkDataSource: NewsNetworkDataSource,
    private val newsSourcesDao: NewsSourcesDao
) : NewsRepository {

    var sourcesList = mutableListOf<String>()

    override suspend fun generateSourcesList() {

        newsSourcesDao.getSourcesList().apply {

            forEach {
                sourcesList.add(getHostName(it.url))
            }

        }

    }

    override suspend fun getAllTheNews(): LiveData<List<Article>> {
        return withContext(Dispatchers.IO) {

            getAllNewsOnRequest()
            return@withContext newsDao.getNews()
        }
    }

    override suspend fun getTopNews(language: String): LiveData<List<Article>> {
        return withContext(Dispatchers.IO) {

            getTopNewsNow(language)
            return@withContext newsDao.getNews()

        }
    }


    override suspend fun getNewsFromSource(domains: String): LiveData<List<Article>> {
        return withContext(Dispatchers.IO) {

            getAllNewsFromSources(domains)
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

        fun deleteOldFetchedNews() {
            newsDao.deleteOldNews()
        }

        GlobalScope.launch(Dispatchers.IO) {

            deleteOldFetchedNews()
            val newsList = fetchedNews.articles
            newsDao.upsert(newsList)
        }
    }

    override suspend fun getNews(): LiveData<List<Article>> {

        return withContext(Dispatchers.IO) {

            getAllNews()
            return@withContext newsDao.getNews()
        }
    }

    private suspend fun getAllNews() {
        newsNetworkDataSource.fetchNews("india")
    }

    private suspend fun getAllNewsFromSources(domains: String) {
        newsNetworkDataSource.fetchNewsFromSource(domains)
    }

    private suspend fun getTopNewsNow(language: String) {

        newsNetworkDataSource.fetchTopNews(language)

    }

    private suspend fun getAllNewsOnRequest() {

        generateSourcesList()
        Log.d("List of Sources", "$sourcesList")
        newsNetworkDataSource.fetchNewsFromSource(listOf("abcnews.go.com, abc.net.au, aljazeera.com, arstechnica.com, apnews.com, afr.com, axios.com, bbc.co.uk, bbc.co.uk, bleacherreport.com, bloomberg.com, breitbart.com, businessinsider.com").joinToString())
    }
}