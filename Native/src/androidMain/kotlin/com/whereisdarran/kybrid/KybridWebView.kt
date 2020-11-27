package com.whereisdarran.kybrid

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import android.webkit.WebViewClient




actual class KybridWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): WebView(context, attrs, defStyleAttr)  {

    init {
        webViewClient = KybridBrowser()
    }

    actual fun loadKybridUrl(url: String) {
        super.loadUrl(url)
        PluginRegistry.initializePlugins()
    }


    private class KybridBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}