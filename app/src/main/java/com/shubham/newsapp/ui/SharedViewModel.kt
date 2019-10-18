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

    //private val domain = MutableLiveData<String>()

    private var domain: String? = null

    lateinit var newsFromSources: Deferred<LiveData<List<Article>>>

    fun selectedDomain(value: String?){
        domain = value
    }

    fun returnDomain(): String?{

        return if (domain == null)
            null
        else
            domain
    }

    val news by lazyDeferred {
        newsRepository.getNews()
    }

//    val newsFromSource by lazyDeferred {
//
//        newsRepository.getNewsFromSource(getHostName(domain!!))
//    }

    val newsSources by lazyDeferred {
        newsSourceRepository.getNewsSources()
    }


    val topNews by lazyDeferred{
        newsRepository.getTopNews("en")
    }

    fun test(){

        newsFromSources = GlobalScope.async {

             newsRepository.getNewsFromSource(getHostName(domain!!))
        }
    }

}

