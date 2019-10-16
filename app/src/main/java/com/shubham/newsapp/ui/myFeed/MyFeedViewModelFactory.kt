package com.shubham.newsapp.ui.myFeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shubham.newsapp.data.repository.NewsRepository

@Suppress("UNCHECKED_CAST")
class MyFeedViewModelFactory(
    private val newsRepository: NewsRepository
): ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return MyFeedViewModel(newsRepository) as T
    }
}