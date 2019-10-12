package com.shubham.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.shubham.newsapp.data.db.dao.NewsSourcesDao
import com.shubham.newsapp.data.db.entity.SourceX
import com.shubham.newsapp.data.network.dataSource.NewsSourcesNetworkDataSource
import com.shubham.newsapp.data.network.response.NewsSourcesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsSourceRepositoryImpl(
    private val newsSourcesDao: NewsSourcesDao,
    private val newsSourcesNetworkDataSource: NewsSourcesNetworkDataSource
) : NewsSourceRepository {

    override suspend fun getNewsSources(): LiveData<List<SourceX>> {
        return withContext(Dispatchers.IO){

            fetchNewsSource()
            return@withContext newsSourcesDao.getSources()
        }
    }

    init {
        newsSourcesNetworkDataSource.apply {
            downloadedNewsSources.observeForever { newNewsSource ->
                persistFetchedNewsSources(newNewsSource)
            }
        }
    }

    private fun persistFetchedNewsSources(newNewsSource: NewsSourcesResponse?) {


        fun deleteOldSources(){
            newsSourcesDao.deleteOldSources()
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldSources()
            val newSourcesList = newNewsSource?.sources
            newsSourcesDao.insert(newSourcesList!!)
        }
    }

    private suspend fun fetchNewsSource(){

        newsSourcesNetworkDataSource.fetchNewsSources()
    }
}