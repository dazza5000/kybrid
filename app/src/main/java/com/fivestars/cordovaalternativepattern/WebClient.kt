package com.fivestars.cordovaalternativepattern

import android.webkit.WebView
import android.webkit.WebViewClient
import com.fivestars.cordovaalternativepattern.bluetooth.JavascriptMessageHandler

class WebClient : WebViewClient() {

    private lateinit var javascriptMessageHandler: JavascriptMessageHandler

    override fun onPageFinished(webView: WebView, url: String) {
        super.onPageFinished(webView, url)
        javascriptMessageHandler =
            JavascriptMessageHandler(
                webView
            )
    }

}