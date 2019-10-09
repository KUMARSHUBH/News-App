package com.shubham.newsapp.ui.discover

import androidx.lifecycle.ViewModel
import com.shubham.newsapp.data.repository.NewsRepository
import com.shubham.newsapp.internal.lazyDeferred

class DiscoverViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val news by lazyDeferred {
        newsRepository.getNews()
    }
}
