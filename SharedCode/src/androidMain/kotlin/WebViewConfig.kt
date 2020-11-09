package com.fivestars.cordovaalternativepattern

import android.webkit.WebSettings
import android.webkit.WebView

class WebViewConfig(webView: WebView) {

    init {
        webView.settings.setSupportZoom(true)
        webView.settings.setGeolocationEnabled(true)
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.allowFileAccess = true
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webView.webViewClient = WebClient()
        webView.loadUrl("file:///android_asset/index.html")
    }

}