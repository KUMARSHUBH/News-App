package com.shubham.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.shubham.newsapp.data.db.entity.Article

interface NewsRepository {

    suspend fun getNews() : LiveData<List<Article>>
}