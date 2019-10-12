package com.shubham.newsapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shubham.newsapp.data.db.entity.SourceX

@Dao
interface NewsSourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsSources : List<SourceX>)

    @Query("select * from news_sources")
    fun getSources() : LiveData<List<SourceX>>

    @Query("delete from news_sources")
    fun deleteOldSources()
}