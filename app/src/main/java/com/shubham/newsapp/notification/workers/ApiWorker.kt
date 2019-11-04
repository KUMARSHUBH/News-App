package com.shubham.newsapp.notification.workers

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shubham.newsapp.data.network.NewsApiservice
import com.shubham.newsapp.internal.getHostName
import com.shubham.newsapp.notification.sendNotification
import java.util.*
import kotlin.random.Random

class ApiWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val newsApiservice: NewsApiservice
) :
    CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {

        try {

            val selector = Random.nextInt(0,9)
            var source = when(selector){

                0  -> "hindustantimes.com"
                1 -> "arstechnica.com"
                2 -> "business-standard.com"
                3 -> "ndtv.com"
                5 -> "timesofindia.indiatimes.com"
                6 -> "bleacherreport.com"
                7 -> "bloomberg.com"
                8 -> "thehansindia.com"
                9 -> "businessinsider.com"
                else -> ""
            }



            val articles = newsApiservice.getAllTheNews(source).await()

            val count= articles.articles.size

            val show = articles.articles


            Log.d("-----------NEWS--------", show.toString())

            val article = articles.articles[Random.nextInt(0,count)]

            Log.d("NEWS", articles.articles.toString())
            val title = article.title
            val imageUrl = article.urlToImage
            val url = article.url

            val domain = getHostName(article.url!!)

            val notificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager


            notificationManager.sendNotification(
               title!!,
                imageUrl!!,
                url!!,
                applicationContext,
                domain,
                Calendar.getInstance().timeInMillis
            )


            return Result.success()

        } catch (e: Exception) {

//            Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()

            e.printStackTrace()
            return Result.retry()
        }

    }


}