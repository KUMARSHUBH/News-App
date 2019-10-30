package com.shubham.newsapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.shubham.newsapp.R
import com.shubham.newsapp.ui.MainActivity
import com.squareup.picasso.Picasso
import kotlin.random.Random

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(
    title: String,
    imageUrl: String,
    url: String,
    applicationContext: Context
) {


    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(Picasso.get().load(imageUrl).get())
        .bigLargeIcon(null)


    val intent = Intent(applicationContext,MainActivity::class.java)
    intent.putExtra("from_notification",true)


    val shareIntent = Intent(applicationContext,ShareReceiver::class.java)
    shareIntent.putExtra("url",url)
    val sharePendingIntent = PendingIntent.getBroadcast(applicationContext, REQUEST_CODE,shareIntent,PendingIntent.FLAG_UPDATE_CURRENT)

    val pendingIntent = PendingIntent.getActivity(applicationContext,
        REQUEST_CODE,intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val builder = NotificationCompat.Builder(
        applicationContext,"Notification"

    )

        .setSmallIcon(R.drawable.newspaper)
        .setColor(R.drawable.newspaper)
        .setContentTitle(title)
        .setStyle(bigPicStyle)
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        .addAction(R.drawable.ic_share_24dp,
            "share",sharePendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(longArrayOf(0,250,250,250))
        .setLights(Color.BLUE,500,500)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))


    createChannel("Notification", "News Notification",applicationContext)

    notify(Random.nextInt(10),builder.build())
}


fun createChannel(channelId: String, channelName: String,context: Context) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "Latest News Notification"
        notificationChannel.setShowBadge(false)

        val notificationManager = context.getSystemService(
            NotificationManager::class.java
        )

        notificationManager?.createNotificationChannel(notificationChannel)

    }}

