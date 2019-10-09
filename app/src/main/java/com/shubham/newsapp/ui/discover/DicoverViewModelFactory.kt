package com.shubham.newsapp.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shubham.newsapp.data.repository.NewsRepository

@Suppress("UNCHECKED_CAST")
class DicoverViewModelFactory(
    private val newsRepository: NewsRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiscoverViewModel(newsRepository) as T
    }
}
