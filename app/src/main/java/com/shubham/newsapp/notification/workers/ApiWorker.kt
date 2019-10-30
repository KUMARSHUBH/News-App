package com.shubham.newsapp.notification.workers

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shubham.newsapp.data.network.NewsApiservice
import com.shubham.newsapp.notification.sendNotification

class ApiWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val newsApiservice: NewsApiservice
) :
    CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {


        try {

            val article = newsApiservice.getTopNews("en").await().articles[0]

            val title = article.title
            val imageUrl = article.urlToImage
            val url = article.url

            val notificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager


            notificationManager.sendNotification(
               title!!,
                imageUrl!!,
                url!!,
                applicationContext
            )


//            notificationManager.testNotification("AV",applicationContext)
            return Result.success()

        } catch (e: Exception) {

            return Result.failure()
        }

    }


}