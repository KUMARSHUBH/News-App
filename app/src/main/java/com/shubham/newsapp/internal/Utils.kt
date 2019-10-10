package com.shubham.newsapp.internal

import android.R
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import java.net.MalformedURLException
import java.net.URL


fun getHostName(urlInput: String): String {
    var urlInput = urlInput
    urlInput = urlInput.toLowerCase()
    var hostName = urlInput
    if (urlInput != "") {
        if (urlInput.startsWith("http") || urlInput.startsWith("https")) {
            try {
                val netUrl = URL(urlInput)
                val host = netUrl.host
                if (host.startsWith("www")) {
                    hostName = host.substring("www".length + 1)
                } else {
                    hostName = host
                }
            } catch (e: MalformedURLException) {
                hostName = urlInput
            }

        } else if (urlInput.startsWith("www")) {
            hostName = urlInput.substring("www".length + 1)
        }
        return hostName
    } else {
        return ""
    }
}


fun getMostPopulousSwatch(palette: Palette?): Palette.Swatch? {
    var mostPopulous: Palette.Swatch? = null
    if (palette != null) {
        for (swatch in palette.swatches) {
            if (mostPopulous == null || swatch.population > mostPopulous.population) {
                mostPopulous = swatch
            }
        }
    }
    return mostPopulous
}


//com.shubham.newsapp.R.color.grey_800

fun setUpInfoBackgroundColor(ll: TextView, palette: Palette, context: Context) {
    val swatch = getMostPopulousSwatch(palette)
    if (swatch != null) {
        val startColor = ContextCompat.getColor(ll.context, R.color.white)
        val endColor = swatch.rgb

        animateBackgroundColorChange(ll, startColor, endColor)
    }
}

fun animateBackgroundColorChange(view: View, startColor: Int, endColor: Int) {
    val infoBackgroundColorAnim = ValueAnimator.ofArgb(startColor, endColor)
    infoBackgroundColorAnim.addUpdateListener { animation -> view.setBackgroundColor(animation.animatedValue as Int) }
    infoBackgroundColorAnim.duration = AnimationUtility.DURATION
    infoBackgroundColorAnim.interpolator =
        AnimationUtility.getFastOutSlowInInterpolator(view.context)
    infoBackgroundColorAnim.start()
}