package com.shubham.newsapp.ui

import androidx.lifecycle.ViewModel
import com.shubham.newsapp.data.repository.NewsRepository
import com.shubham.newsapp.data.repository.NewsSourceRepository
import com.shubham.newsapp.internal.getHostName
import com.shubham.newsapp.internal.lazyDeferred

class SharedViewModel(
    private val newsRepository: NewsRepository,
    private val newsSourceRepository: NewsSourceRepository

) : ViewModel() {

    //private val domain = MutableLiveData<String>()

    private var domain: String? = null

    fun selectedDomain(value: String){
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

    val newsFromSource by lazyDeferred {

        newsRepository.getNewsFromSource(getHostName(domain.toString()))
    }

    val newsSources by lazyDeferred {
        newsSourceRepository.getNewsSources()
    }

}