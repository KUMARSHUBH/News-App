package com.shubham.newsapp.ui.myFeed

import androidx.lifecycle.ViewModel
import com.shubham.newsapp.data.repository.NewsRepository
import com.shubham.newsapp.internal.lazyDeferred

class MyFeedViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val news by lazyDeferred {
        newsRepository.getNews()
    }
}
