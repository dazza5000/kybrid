package com.whereisdarran.kybrid

import platform.Foundation.setValue
import platform.UIKit.UIScreen
import platform.WebKit.WKScriptMessage
import platform.WebKit.WKUserContentController
import platform.WebKit.WKUserScript
import platform.WebKit.WKUserScriptInjectionTime
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.darwin.NSObject

actual class PlatformWebView(private val wkWebView: WKWebView) {
    private var incomingMessageCallback: IncomingMessageCallback? = null

    actual fun initialize(pluginConfig: PluginConfig) {

    }

    actual fun sendMessage(message: String) {
        val outgoingMessage = "window.com.whereisdarran.kybrid.handleMessage(new MessageEvent(\"message\",{" +
                "data : $message }))"

        println("sending the following message: $outgoingMessage")
        wkWebView.evaluateJavaScript(outgoingMessage, null)
    }

    actual fun setIncomingMessageCallback(incomingMessageCallback: IncomingMessageCallback) {
        this.incomingMessageCallback = incomingMessageCallback
    }
}