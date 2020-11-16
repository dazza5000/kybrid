package com.whereisdarran.kybrid

import android.webkit.WebMessagePort
import android.webkit.WebView

data class Ports(val nativePort: WebMessagePort, val webViewPort: WebMessagePort) {
    companion object {
        fun fromWebView(webView: WebView): Ports {
            val channel = webView.createWebMessageChannel()
            return Ports(
                nativePort = channel[0],
                webViewPort = channel[1]
            )
        }
    }
}
