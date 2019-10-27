package com.shubham.newsapp

import android.app.Application
import androidx.preference.PreferenceManager
import com.shubham.newsapp.data.db.NewsDatabase
import com.shubham.newsapp.data.network.NewsApiservice
import com.shubham.newsapp.data.network.dataSource.NewsNetworkDataSource
import com.shubham.newsapp.data.network.dataSource.NewsNetworkDataSourceImpl
import com.shubham.newsapp.data.network.dataSource.NewsSourcesNetworkDataSource
import com.shubham.newsapp.data.network.dataSource.NewsSourcesNetworkDataSourceImpl
import com.shubham.newsapp.data.network.interceptor.ConnectivityInterceptor
import com.shubham.newsapp.data.network.interceptor.ConnectivityInterceptorImpl
import com.shubham.newsapp.data.repository.NewsRepository
import com.shubham.newsapp.data.repository.NewsRepositoryImpl
import com.shubham.newsapp.data.repository.NewsSourceRepository
import com.shubham.newsapp.data.repository.NewsSourceRepositoryImpl
import com.shubham.newsapp.ui.SharedViewModelFactory
import example.com.darkthemeplayground.settings.ThemeHelper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@NewsApplication))

        bind() from singleton { NewsDatabase(instance()) }
        bind() from singleton { instance<NewsDatabase>().newsDao() }
        bind() from singleton { instance<NewsDatabase>().newsSourcesDao() }

        bind<ConnectivityInterceptor>() with singleton {
            ConnectivityInterceptorImpl(
                instance()
            )
        }
        bind() from singleton { NewsApiservice(instance()) }

        bind<NewsNetworkDataSource>() with singleton {
            NewsNetworkDataSourceImpl(
                instance()
            )
        }
        bind<NewsSourcesNetworkDataSource>() with singleton {
            NewsSourcesNetworkDataSourceImpl(instance())
        }
        bind<NewsRepository>() with singleton { NewsRepositoryImpl(instance(), instance(), instance()) }

        bind<NewsSourceRepository>() with singleton {
            NewsSourceRepositoryImpl(instance(),instance())
        }

        bind() from provider { SharedViewModelFactory(instance(),instance()) }
    }


    override fun onCreate() {
        super.onCreate()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = prefs.getString(getString(R.string.theme_pref_key), ThemeHelper.DEFAULT_MODE)
        ThemeHelper.applyTheme(themePref!!)
    }
}