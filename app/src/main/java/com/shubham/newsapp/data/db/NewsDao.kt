package com.shubham.newsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shubham.newsapp.data.db.entity.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun upsert(article: List<Article>)

    @Query("select * from news")
    fun getNews(): LiveData<List<Article>>

    @Query("delete from news")
    fun deleteOldNews()
}