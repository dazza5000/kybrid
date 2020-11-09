package com.fivestars.cordovaalternativepattern.bluetooth

import android.net.Uri
import android.util.Log
import android.webkit.WebView
import androidx.webkit.WebMessageCompat
import androidx.webkit.WebMessagePortCompat
import androidx.webkit.WebViewCompat
import com.fivestars.cordovaalternativepattern.KEY_CAPTURE_PORT
import com.fivestars.cordovaalternativepattern.KEY_MAC_ADDRESS
import com.fivestars.cordovaalternativepattern.KEY_SEND_DATA
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerial.MessageHandler
import com.fivestars.cordovaalternativepattern.model.Action
import com.fivestars.cordovaalternativepattern.model.Callback
import kotlinx.serialization.json.Json
import com.fivestars.cordovaalternativepattern.model.message.JavascriptMessage
import com.fivestars.cordovaalternativepattern.model.message.NativeDataMessage


class JavascriptMessageHandler(webView: WebView) {

    private val LOG_TAG = "JavascriptHandler"
    private val webMessagePorts = WebViewCompat.createWebMessageChannel(webView)
    private var javascriptToNativeCallback: WebMessagePortCompat.WebMessageCallbackCompat? = null
    private val json = Json

    init {

        javascriptToNativeCallback = object : WebMessagePortCompat.WebMessageCallbackCompat() {
            override fun onMessage(port: WebMessagePortCompat, message: WebMessageCompat?) {
                super.onMessage(port, message)

                if (message?.data == null) {
                    Log.d(LOG_TAG, "Invalid message.")
                    return
                }

                val javascriptMessage = json.decodeFromString(JavascriptMessage.serializer(), message.data!!)

                when (javascriptMessage.action) {
                    Action.CONNECT -> BluetoothSerial.connect(
                        javascriptMessage.data!![KEY_MAC_ADDRESS] as String,
                        javascriptMessage.successCallback,
                        javascriptMessage.failureCallback
                    )
                    Action.DISCONNECT -> {
                        BluetoothSerial.disconnect(javascriptMessage.successCallback, javascriptMessage.failureCallback)
                    }
                    Action.SEND -> BluetoothSerial.write(
                        javascriptMessage.data!![KEY_SEND_DATA] as String,
                        javascriptMessage.successCallback,
                        javascriptMessage.failureCallback
                    )
                    Action.LISTEN -> { BluetoothSerial.listen(javascriptMessage.successCallback, javascriptMessage.failureCallback)}
                    Action.GET_ADDRESS -> {BluetoothSerial.getAddress(javascriptMessage.successCallback, javascriptMessage.failureCallback)}
                    Action.REGISTER_DATA_CALLBACK -> { BluetoothSerial.registerOnDataCallback(
                        Callback.ON_DATA_CALLBACK
                    )}
                    Action.REGISTER_CONNECT_CALLBACK -> {
                        BluetoothSerial.registerConnectCallback(
                            Callback.ON_CONNECT_CALLBACK
                        )
                    }
                    Action.REGISTER_DISCONNECT_CALLBACK -> {
                        BluetoothSerial.registerDisconnectCallback(
                            Callback.ON_CLOSE_CALLBACK
                        )
                    }
                }
            }
        }

        val destPort = arrayOf(webMessagePorts[1])
        webMessagePorts[0].setWebMessageCallback(javascriptToNativeCallback!!)
        WebViewCompat.postWebMessage(webView, WebMessageCompat(KEY_CAPTURE_PORT, destPort), Uri.EMPTY)

        BluetoothSerial.messageHandler = object : MessageHandler {
            override fun sendMessage(nativeDataMessage: NativeDataMessage) {
                val jsonData =
                    json.encodeToString(NativeDataMessage.serializer(), nativeDataMessage)
                WebViewCompat.postWebMessage(webView, WebMessageCompat(jsonData), Uri.EMPTY)
            }
        }
    }
}