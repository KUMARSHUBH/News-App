package com.shubham.newsapp

import android.app.Application
import com.shubham.newsapp.data.db.NewsDatabase
import com.shubham.newsapp.data.network.*
import com.shubham.newsapp.data.repository.NewsRepository
import com.shubham.newsapp.data.repository.NewsRepositoryImpl
import com.shubham.newsapp.ui.myFeed.MyFeedViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsApplication : Application(),KodeinAware{

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@NewsApplication))

        bind() from singleton { NewsDatabase(instance()) }
        bind() from singleton { instance<NewsDatabase>().newsDao()}
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { NewsApiservice(instance()) }
        bind<NewsNetworkDataSource>() with singleton { NewsNetworkDataSourceImpl(instance()) }
        bind<NewsRepository>() with singleton { NewsRepositoryImpl(instance(),instance()) }
        bind() from provider { MyFeedViewModelFactory(instance()) }
    }
}