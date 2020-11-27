package com.whereisdarran.kybrid

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import android.webkit.WebViewClient


actual class KybridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): WebView(context, attrs, defStyleAttr)  {

    val platformWebView: PlatformWebView

    init {
        webViewClient = KybridBrowser()
        platformWebView = PlatformWebView(this)
    }

    actual fun loadKybridUrl(url: String) {
        super.loadUrl(url)
        com.whereisdarran.kybrid.core.PluginRegistry.initializePlugins(platformWebView)
    }

    private class KybridBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}