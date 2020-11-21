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

actual class NativePlugin {
    private lateinit var wkWebView: WKWebView
    private var incomingMessageCallback: IncomingMessageCallback? = null

    actual fun initialize(pluginConfig: PluginConfig) {
        println("Initializing WKWebView")

        val wkWebViewConfiguration = WKWebViewConfiguration()
        wkWebViewConfiguration.preferences.setValue(true, forKey = "allowFileAccessFromFileURLs")
        wkWebViewConfiguration.setValue(true, forKey = "allowUniversalAccessFromFileURLs")
        val source = "document.addEventListener('message', function(){ window.webkit.messageHandlers.webListener.postMessage('click clack!'); })"
        val script = WKUserScript(source, injectionTime = WKUserScriptInjectionTime.WKUserScriptInjectionTimeAtDocumentEnd, false)
        wkWebViewConfiguration.userContentController.addUserScript(script)

        val scriptMessageHandler: platform.WebKit.WKScriptMessageHandlerProtocol = object : platform.WebKit.WKScriptMessageHandlerProtocol, NSObject() {
            override fun userContentController(
                userContentController: WKUserContentController,
                didReceiveScriptMessage: WKScriptMessage
            ) {
                println("Received the following message ${didReceiveScriptMessage.body}")
                incomingMessageCallback?.onIncomingMessageCallback(didReceiveScriptMessage.body as String)
            }
        }

        wkWebViewConfiguration.userContentController.addScriptMessageHandler(scriptMessageHandler, "webListener")

        this.wkWebView = WKWebView(frame = UIScreen.mainScreen.bounds, wkWebViewConfiguration)
    }

    actual fun sendMessage(message: String) {
        val outgoingMessage = "window.pointofsale.com.whereisdarran.kybrid.handleMessage(new MessageEvent(\"message\",{" +
                "data : $message }))"

        println("sending the following message: $outgoingMessage")
        wkWebView.evaluateJavaScript(outgoingMessage, null)
    }

    actual fun setIncomingMessageCallback(incomingMessageCallback: IncomingMessageCallback) {
        this.incomingMessageCallback = incomingMessageCallback
    }
}
