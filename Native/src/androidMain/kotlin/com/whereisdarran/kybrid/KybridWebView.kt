package com.whereisdarran.kybrid

import android.content.Context
import android.webkit.WebView

actual class KybridWebView(context: Context): WebView(context) {
    actual override fun loadUrl(url: String) {
        super.loadUrl(url)
        PluginRegistry.initializePlugins()
    }
}