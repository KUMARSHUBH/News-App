package com.shubham.newsapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

class ShareReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND

        val bundle = intent?.getStringExtra("url")
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Check out this news article\n\n$bundle"
        )

        shareIntent.type = "text/plain"
        startActivity(context!!,Intent.createChooser(shareIntent, "Share this article with..."),null)
    }

}