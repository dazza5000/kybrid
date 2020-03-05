package com.fivestars.cordovaalternativepattern

import android.net.Uri
import android.webkit.WebView
import android.widget.Toast
import androidx.webkit.WebMessageCompat
import androidx.webkit.WebMessagePortCompat
import androidx.webkit.WebViewCompat
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerial
import com.fivestars.cordovaalternativepattern.model.Action
import com.fivestars.cordovaalternativepattern.model.CallbackId
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import model.JavascriptMessage


class PostMessageHandler(webView: WebView) {

    private val webMessagePorts = WebViewCompat.createWebMessageChannel(webView)
    private var javascriptToNativeCallback: WebMessagePortCompat.WebMessageCallbackCompat? = null
    private val json = Json(JsonConfiguration.Stable)

    init {

        javascriptToNativeCallback = object : WebMessagePortCompat.WebMessageCallbackCompat() {
            override fun onMessage(port: WebMessagePortCompat, message: WebMessageCompat?) {
                super.onMessage(port, message)
                Toast.makeText(webView.context, message!!.data, Toast.LENGTH_SHORT).show()

                val javascriptMessage = json.parse(JavascriptMessage.serializer(), message.data!!)

                when (javascriptMessage?.action) {
                    Action.CONNECT -> BluetoothSerial.connect(javascriptMessage.data!![KEY_MAC_ADDRESS] as String, javascriptMessage.successCallbackId, javascriptMessage.failureCallbackId, webView)
                    Action.DISCONNECT -> {}
                    Action.SEND -> BluetoothSerial.write(javascriptMessage.data!![KEY_SEND_DATA] as String, javascriptMessage.successCallbackId, javascriptMessage.failureCallbackId, webView)
                    Action.LISTEN -> {}
                    Action.GET_ADDRESS -> {}
                    Action.REGISTER_DATA_CALLBACK -> { BluetoothSerial.registerOnDataCallback(CallbackId.ON_DATA_CALLBACK, webView)}
                    Action.REGISTER_CONNECT_CALLBACK -> {}
                    Action.REGISTER_CLOSE_CALLBACK -> {}
                    null ->  {}
                }
            }
        }

        val destPort = arrayOf(webMessagePorts[1])
        webMessagePorts[0].setWebMessageCallback(javascriptToNativeCallback!!)
        WebViewCompat.postWebMessage(webView, WebMessageCompat(KEY_CAPTURE_PORT, destPort), Uri.EMPTY)

    }
}