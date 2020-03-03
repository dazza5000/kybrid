package com.fivestars.cordovaalternativepattern

import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast

class ChromeClient(val webView: WebView) : WebChromeClient() {

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        Toast.makeText(webView.context, consoleMessage?.message(), Toast.LENGTH_SHORT).show()
        return super.onConsoleMessage(consoleMessage)
    }

}