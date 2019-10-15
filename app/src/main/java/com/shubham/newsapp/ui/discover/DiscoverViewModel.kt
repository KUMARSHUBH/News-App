package com.shubham.newsapp.ui.discover

import androidx.lifecycle.ViewModel
import com.shubham.newsapp.data.repository.NewsSourceRepository
import com.shubham.newsapp.internal.lazyDeferred

class DiscoverViewModel(
    private val newsSourceRepository: NewsSourceRepository
) : ViewModel() {

    val newsSources by lazyDeferred {
        newsSourceRepository.getNewsSources()
    }

}