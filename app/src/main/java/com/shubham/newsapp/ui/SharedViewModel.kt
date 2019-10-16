package com.shubham.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shubham.newsapp.data.repository.NewsRepository
import com.shubham.newsapp.data.repository.NewsSourceRepository
import com.shubham.newsapp.internal.lazyDeferred

class SharedViewModel(
    private val newsRepository: NewsRepository,
    private val newsSourceRepository: NewsSourceRepository

) : ViewModel() {

    private val domain = MutableLiveData<String>()

    fun selectedDomain(value: String){
        domain.value = value
    }

    fun returnDomain(): String{
        return domain.value.toString()
    }

    val news by lazyDeferred {
        newsRepository.getNews()
    }

    val newsFromSource by lazyDeferred {

        newsRepository.getNewsFromSource(domain.toString())
    }

    val newsSources by lazyDeferred {
        newsSourceRepository.getNewsSources()
    }

}