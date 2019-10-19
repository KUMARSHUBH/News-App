package com.shubham.newsapp.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shubham.newsapp.data.network.interceptor.ConnectivityInterceptor
import com.shubham.newsapp.data.network.response.NewsResponse
import com.shubham.newsapp.data.network.response.NewsSourcesResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "cf5fbd760ef24c8d8d538c7743647c8b"

//

interface NewsApiservice {


    @GET("top-headlines")
    fun getAllNews(
        @Query("q") query:String
    ): Deferred<NewsResponse>

    @GET("top-headlines")
    fun getTopNews(
        @Query("language") language: String = "en"
    ) : Deferred<NewsResponse>

    @GET("sources")
    fun getSources(
        @Query("language") language : String = "en"
    ) : Deferred<NewsSourcesResponse>

    @GET("everything")
    fun getEverything(
        @Query("domains") domains: String
    ) : Deferred<NewsResponse>

    @GET("everything")
    fun getAllTheNews(
        @Query("domains") domains: String,
        @Query("sortBy") sortBy: String = "popularity"
    ) : Deferred<NewsResponse>

    @GET("top-headlines")
    fun getTopCategoryRelatedNews(
        @Query("country") country: String,
        @Query("category") category: String
    ) : Deferred<NewsResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): NewsApiservice{

            val requestInterceptor = Interceptor {chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(NewsApiservice::class.java)
        }
    }
}