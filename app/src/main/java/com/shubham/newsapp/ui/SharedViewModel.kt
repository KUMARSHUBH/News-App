package com.shubham.newsapp.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.data.repository.NewsRepository
import com.shubham.newsapp.data.repository.NewsSourceRepository
import com.shubham.newsapp.internal.getHostName
import com.shubham.newsapp.internal.lazyDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class SharedViewModel(
    private val newsRepository: NewsRepository,
    private val newsSourceRepository: NewsSourceRepository

) : ViewModel() {


    private var domain: String? = null
    private var selected: String? = null
    private var category: String? = null

    lateinit var newsFromSources: Deferred<LiveData<List<Article>>>
    lateinit var topNews: Deferred<LiveData<List<Article>>>
    lateinit var allNews: Deferred<LiveData<List<Article>>>
    lateinit var topNewsCategory: Deferred<LiveData<List<Article>>>


    fun selectedDomain(value: String?) {
        domain = value
    }
    fun returnSelected(): String? {

        return if (selected == null)
            null
        else
            selected
    }
    fun selectedItem(value: String?) {

        selected = value
    }
    fun returnDomain(): String? {

        return if (domain == null)
            null
        else
            domain
    }
    fun selectedCategory(value: String?){

        category = value
    }
    fun returnCategory() : String?{

        return if (category == null)
            null
        else
            category
    }


    val news by lazyDeferred {
        newsRepository.getNews()
    }

    val newsSources by lazyDeferred {
        newsSourceRepository.getNewsSources()
    }


    fun fetchTopNews() {

        topNews = GlobalScope.async {

            newsRepository.getTopNews("en")
        }
    }

    fun fetchNewsFromSources() {

        newsFromSources = GlobalScope.async {

            newsRepository.getNewsFromSource(getHostName(domain!!))
        }
    }
    fun fetchAllNews() {

        allNews = GlobalScope.async {

            newsRepository.getAllTheNews()
        }
    }

    fun fetchTopHeadlinesCategory(){

        topNewsCategory = GlobalScope.async {

            newsRepository.getTopHeadlinesFromCategory(category!!)
        }
    }

}

