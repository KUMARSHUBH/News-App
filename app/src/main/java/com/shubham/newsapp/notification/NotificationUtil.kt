package com.shubham.newsapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.shubham.newsapp.R
import com.shubham.newsapp.ui.MainActivity
import com.squareup.picasso.Picasso

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(
    title: String,
    imageUrl: String,
    url: String,
    applicationContext: Context,
    domain: String,
    whens : Long
) {


//    val bigPicStyle = NotificationCompat.BigPictureStyle()
//        .bigPicture(Picasso.get().load(imageUrl).get())
//        .bigLargeIcon(null)

    val remoteCollapsedView = RemoteViews(applicationContext.packageName,R.layout.custom_normal_notification)
    remoteCollapsedView.setTextViewText(R.id.collapsed_text,title)
    val remoteExpandedView = RemoteViews(applicationContext.packageName,R.layout.custom_expanded_notification)
    remoteExpandedView.setImageViewBitmap(R.id.image_news,Picasso.get().load(imageUrl).get())
    remoteExpandedView.setTextViewText(R.id.tv_news_title,title)

//    val remoteViews = RemoteViews(applicationContext.packageName,R.layout.notification_layout)
//    remoteViews.setImageViewBitmap(R.id.notification_textView,Picasso.get().load(imageUrl).get())
//    remoteViews.setTextViewText(R.id.notification_textView,title)

    val intent = Intent(applicationContext,MainActivity::class.java)
    intent.putExtra("title",title)
    intent.putExtra("from_Notification",true)
    intent.data = Uri.parse("content://$whens")

    val shareIntent = Intent(applicationContext,ShareReceiver::class.java)
    shareIntent.putExtra("url",url)
    val sharePendingIntent = PendingIntent.getBroadcast(applicationContext, REQUEST_CODE,shareIntent,PendingIntent.FLAG_UPDATE_CURRENT)

    val pendingIntent = PendingIntent.getActivity(applicationContext,
        REQUEST_CODE,intent, PendingIntent.FLAG_UPDATE_CURRENT)
    val builder = NotificationCompat.Builder(
        applicationContext,"Notification"

    )
        .setWhen(whens)
        .setSmallIcon(R.drawable.ic_stat_news_logo)
        .setColor(Color.BLUE)
//        .setContentTitle(title)
//        .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources,R.mipmap.ic_launcher))
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setCustomContentView(remoteCollapsedView)
        .setCustomBigContentView(remoteExpandedView)
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .addAction(R.drawable.ic_share_24dp,
            "share",sharePendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(longArrayOf(0,250,250,250))
        .setLights(Color.BLUE,500,500)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))



    createChannel("Notification", "News Notification",applicationContext)

    notify(whens.toInt(),builder.build())
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

