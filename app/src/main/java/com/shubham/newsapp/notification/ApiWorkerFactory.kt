package com.shubham.newsapp.notification

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.shubham.newsapp.data.network.NewsApiservice
import com.shubham.newsapp.notification.workers.ApiWorker

class ApiWorkerFactory(private val newsApiservice: NewsApiservice) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {


        return ApiWorker(appContext,workerParameters,newsApiservice)
    }
}