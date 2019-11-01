package com.shubham.newsapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.data.db.entity.Bookmark

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun upsert(article: List<Article>)

    @Query("select * from news")
    fun getNews(): LiveData<List<Article>>

    @Query("delete from news")
    fun deleteOldNews()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBookmark(bookmark: Bookmark)

    @Query("select * from news_bookmark")
    fun getBookmarks(): LiveData<List<Bookmark>>

    @Query("delete from news_bookmark where rowid match :id")
    fun deleteBookmarkedNews(id: Int)

}