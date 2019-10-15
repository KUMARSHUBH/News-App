package com.shubham.newsapp.ui.myFeed

import androidx.lifecycle.ViewModel
import com.shubham.newsapp.data.repository.NewsRepository
import com.shubham.newsapp.internal.lazyDeferred

class MyFeedViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var domain: String = "wsj.com"

    val news by lazyDeferred {
        newsRepository.getNews()
    }

    val newsFromSource by lazyDeferred {

        newsRepository.getNewsFromSource(domain)
    }
}