package com.shubham.newsapp.notification.workers

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shubham.newsapp.data.network.NewsApiservice
import com.shubham.newsapp.internal.getHostName
import com.shubham.newsapp.notification.sendNotification
import kotlin.random.Random

class ApiWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val newsApiservice: NewsApiservice
) :
    CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {


        try {

            val article = newsApiservice.getAllTheNews(listOf("abcnews.go.com, abc.net.au, aljazeera.com, " +
                    "arstechnica.com, apnews.com, afr.com, axios.com, bbc.co.uk," +
                    " bbc.co.uk, bleacherreport.com, bloomberg.com, breitbart.com, businessinsider.com").joinToString()).await().articles[Random.nextInt(0,5)]

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
                domain
            )


            return Result.success()

        } catch (e: Exception) {

            return Result.failure()
        }

    }


}