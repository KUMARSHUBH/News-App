package com.shubham.newsapp.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.shubham.newsapp.R
import com.shubham.newsapp.internal.getHostName
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayShowHomeEnabled(true) // show or hide the default home button
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setDisplayShowCustomEnabled(true) // enable overriding the default toolbar layout
        ab?.setDisplayShowTitleEnabled(false)
        ab?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_24dp)
        val url = intent.getStringExtra("SOURCE_URL")
        url_source.text = getHostName(url)
        initWebView(url)

    }

    private fun initWebView(url: String?) {
        web_view.settings.loadsImagesAutomatically = true
        web_view.settings.domStorageEnabled = true
        web_view.settings.setSupportZoom(true)
        web_view.settings.builtInZoomControls = false
        web_view.settings.displayZoomControls = false
        web_view.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        renderWebpage(url!!)

    }


    private fun renderWebpage(url: String){

        web_view.webViewClient = CustomWebViewClient(pb)
        web_view.webChromeClient = CustomWbChromeClient(pb)
        web_view.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    class CustomWebViewClient(private val pb:ProgressBar) : WebViewClient(){

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            pb.visibility = View.VISIBLE
        }

    }

    class CustomWbChromeClient(private val pb: ProgressBar): WebChromeClient(){

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if(newProgress == 100)
                pb.visibility = View.GONE

            pb.progress = newProgress
        }
    }
}
