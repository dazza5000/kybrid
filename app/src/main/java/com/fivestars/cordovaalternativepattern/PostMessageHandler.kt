package com.fivestars.cordovaalternativepattern

import android.net.Uri
import android.webkit.WebView
import android.widget.Toast
import androidx.webkit.WebMessageCompat
import androidx.webkit.WebMessagePortCompat
import androidx.webkit.WebViewCompat
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerial
import com.fivestars.cordovaalternativepattern.model.Action
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class PostMessageHandler(webView: WebView) {

    private val webMessagePorts = WebViewCompat.createWebMessageChannel(webView)
    private var javascriptToNativeCallback: WebMessagePortCompat.WebMessageCallbackCompat? = null

    init {


        // TODO: Use kotlinx serialization
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        javascriptToNativeCallback = object : WebMessagePortCompat.WebMessageCallbackCompat() {
            override fun onMessage(port: WebMessagePortCompat, message: WebMessageCompat?) {
                super.onMessage(port, message)
                Toast.makeText(webView.context, message!!.data, Toast.LENGTH_SHORT).show()

                val jsonAdapter =
                    moshi.adapter(model.JavascriptMessage::class.java)
                val javascriptMessage = jsonAdapter.fromJson(message.data!!)

                if (Action.CONNECT == javascriptMessage?.action) {
                    BluetoothSerial.connect(javascriptMessage.data!![KEY_MAC_ADDRESS] as String, javascriptMessage.successCallbackId, javascriptMessage.failureCallbackId, webView)
                } else if (Action.SEND == javascriptMessage?.action) {
                    BluetoothSerial.write(javascriptMessage.data!![KEY_SEND_DATA]?.toByteArray()!!, javascriptMessage.successCallbackId, javascriptMessage.failureCallbackId, webView)
                }
            }

        }

        val destPort = arrayOf(webMessagePorts[1])
        webMessagePorts[0].setWebMessageCallback(javascriptToNativeCallback!!)
        WebViewCompat.postWebMessage(webView, WebMessageCompat(KEY_CAPTURE_PORT, destPort), Uri.EMPTY)

    }
}