package com.shubham.newsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shubham.newsapp.data.db.dao.NewsDao
import com.shubham.newsapp.data.db.dao.NewsSourcesDao
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.data.db.entity.Bookmark
import com.shubham.newsapp.data.db.entity.SourceX

@Database(
    entities = [Article::class, SourceX::class, Bookmark::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDao
    abstract fun newsSourcesDao() : NewsSourcesDao

    companion object{
        @Volatile private var instance: NewsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {instance = it}
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                NewsDatabase::class.java,"news_app.db")
                .build()

    }
}