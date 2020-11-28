package com.whereisdarran.kybrid

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebView
import android.webkit.WebViewClient


actual class KybridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): WebView(context, attrs, defStyleAttr)  {

    val platformWebView: PlatformWebView = PlatformWebView(this)

    init {
        settings.apply {
            setSupportZoom(true)
            setGeolocationEnabled(true)
            allowUniversalAccessFromFileURLs = true
            allowFileAccessFromFileURLs = true
            allowFileAccess = true
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            loadsImagesAutomatically = true
            mediaPlaybackRequiresUserGesture = false
        }
    }

    actual fun loadKybridUrl(url: String) {
        webViewClient = KybridBrowser(url, platformWebView)
        super.loadUrl(url)
    }

    private class KybridBrowser(val loadedUrl: String, val platformWebView: PlatformWebView) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (loadedUrl == url) {
                view?.evaluateJavascript("DeviceInfoPlugin.com.whereisdarran.kybrid.plugin.deviceinfo.Registry", { })
                com.whereisdarran.kybrid.core.PluginRegistry.initializePlugins(platformWebView)
            }
        }
    }
}