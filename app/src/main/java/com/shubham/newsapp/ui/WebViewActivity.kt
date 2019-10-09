package com.shubham.newsapp.ui

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.shubham.newsapp.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        setTheme(R.style.AppTheme)

        val url = intent.getStringExtra("SOURCE_URL")
        web_view.webViewClient = WebViewClient()
        web_view.loadUrl(url)

    }
}
