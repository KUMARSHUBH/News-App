package com.shubham.newsapp.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shubham.newsapp.data.repository.NewsSourceRepository

@Suppress("UNCHECKED_CAST")
class DicoverViewModelFactory(
    private val newsSourceRepository: NewsSourceRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiscoverViewModel(newsSourceRepository) as T
    }
}
