package com.shubham.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shubham.newsapp.data.repository.NewsRepository
import com.shubham.newsapp.data.repository.NewsSourceRepository

@Suppress("UNCHECKED_CAST")
class SharedViewModelFactory(
    private val newsRepository: NewsRepository,
    private val newsSourceRepository: NewsSourceRepository

): ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return SharedViewModel(newsRepository,newsSourceRepository) as T
    }
}