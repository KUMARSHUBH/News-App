package com.shubham.newsapp.internal

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
