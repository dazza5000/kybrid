package com.whereisdarran.kybrid

import android.content.Context
import android.webkit.WebView

actual class KybridWebView(context: Context): WebView(context) {
    actual fun loadUrl(url: String) {
        super.loadUrl(url)
        PluginRegistry.initializePlugins()
    }
}