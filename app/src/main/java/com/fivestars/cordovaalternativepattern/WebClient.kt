package com.fivestars.cordovaalternativepattern

import android.webkit.WebView
import android.webkit.WebViewClient

class WebClient : WebViewClient() {

    lateinit var postMessageHandler: PostMessageHandler

    override fun onPageFinished(webView: WebView, url: String) {
        super.onPageFinished(webView, url)
        postMessageHandler = PostMessageHandler(webView)

    }

}