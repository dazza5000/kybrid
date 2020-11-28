package com.whereisdarran.kybrid

import android.net.Uri
import android.os.Handler
import android.util.Log
import android.webkit.WebMessage
import android.webkit.WebMessagePort
import android.webkit.WebView

actual class PlatformWebView(private val webView: WebView) {

    private lateinit var config: PluginConfig
    lateinit var nativePort: WebMessagePort
    private var incomingMessageCallback: IncomingMessageCallback? = null

    private val nativePortCallback = object : WebMessagePort.WebMessageCallback() {
        override fun onMessage(port: WebMessagePort, message: WebMessage) {
            if (message.data == null) {
                Log.d(config.TAG, "Message does not contain data.")
                return
            }
            incomingMessageCallback?.onIncomingMessageCallback(message.data)
        }
    }

    actual fun initialize(pluginConfig: PluginConfig) {
        this.config = pluginConfig
        val (nativePort, webViewPort) = Ports.fromWebView(webView)
        this.nativePort = nativePort

        // setup call back to receive messages from WebView
        this.nativePort.setWebMessageCallback(nativePortCallback)

        // send port down to WebView
        val destPort = arrayOf(webViewPort)
        webView.postWebMessage(WebMessage(config.PORT, destPort), Uri.EMPTY)
    }

    actual fun sendMessage(message: String) {
        /**
         * if this is called before the nativePort is initialized, then drop the message entirely
         * issue was found when integrating TabPay to use this, it was trying to send log statements
         * before everything was set up
         *
         * Note: this should only be relevant during the initial setup
         */
        if (!this::nativePort.isInitialized) {
            return
        }
        nativePort.postMessage(WebMessage(message))
    }

    actual fun setIncomingMessageCallback(incomingMessageCallback: IncomingMessageCallback) {
        this.incomingMessageCallback = incomingMessageCallback
    }
}