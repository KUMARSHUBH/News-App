package com.shubham.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.shubham.newsapp.data.db.entity.SourceX

interface NewsSourceRepository {

    suspend fun getNewsSources() : LiveData<List<SourceX>>
}