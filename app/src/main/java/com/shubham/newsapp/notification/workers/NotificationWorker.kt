package com.shubham.newsapp.notification.workers
//
//import android.app.NotificationManager
//import android.content.Context
//import android.provider.Settings.System.getString
//import android.widget.Toast
//import androidx.core.content.ContextCompat
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.shubham.newsapp.R
//import com.shubham.newsapp.data.network.NewsApiservice
//import com.shubham.newsapp.internal.ARTICLE_IMAGE_URL
//import com.shubham.newsapp.internal.ARTICLE_TITLE
//import com.shubham.newsapp.notification.createChannel
//import com.shubham.newsapp.notification.sendNotification
//
//class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
//CoroutineWorker(appContext, workerParams){
//
//
//    override suspend fun doWork(): Result {
//
//        try {
//
//            createChannel("Notification", "News Notification",applicationContext)
//
//            val notificationManager = ContextCompat.getSystemService(
//                applicationContext,
//                NotificationManager::class.java
//            ) as NotificationManager
//
//
//            notificationManager.sendNotification(inputData.getString(ARTICLE_TITLE)!!,inputData.getString(
//                ARTICLE_IMAGE_URL)!!,applicationContext)
//
//            return Result.success()
//        }
//        catch (e: Exception){
//
//            return Result.failure()
//        }
//}}