package com.fivestars.cordovaalternativepattern

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebView
import android.widget.Toast
import androidx.webkit.WebMessageCompat
import androidx.webkit.WebMessagePortCompat
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerial
import model.JavascriptMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class PostMessageHandler(webView: WebView) {

    private val nativeToJsPorts = WebViewCompat.createWebMessageChannel(webView)
    private var nativeToJs: WebMessagePortCompat.WebMessageCallbackCompat? = null

    init {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        if (WebViewFeature.isFeatureSupported(WebViewFeature.WEB_MESSAGE_CALLBACK_ON_MESSAGE)) {
            nativeToJs = object : WebMessagePortCompat.WebMessageCallbackCompat() {
                override fun onMessage(port: WebMessagePortCompat, message: WebMessageCompat?) {
                    super.onMessage(port, message)
                    Toast.makeText(webView.context, message!!.data, Toast.LENGTH_SHORT).show()

                    val jsonAdapter =
                        moshi.adapter(model.JavascriptMessage::class.java)
                    val javascriptMessage = jsonAdapter.fromJson(message.data!!)

                    if ("connect" == javascriptMessage?.action) {
                        BluetoothSerial.connect(javascriptMessage.data["macAddress"] as String, object: BluetoothSerial.ResultInterface {
                            @SuppressLint("RequiresFeature")
                            override fun sendResult(result: String) {
                                WebViewCompat.postWebMessage(webView, WebMessageCompat("Connected to bluetooth"), Uri.EMPTY)
                            }
                        })
                    }

                    message.data?.run {
                        BluetoothSerial.write(this.toByteArray())
                    }
                }
            }
        }

        val destPort = arrayOf(nativeToJsPorts[1])
        nativeToJsPorts[0].setWebMessageCallback(nativeToJs!!)
        WebViewCompat.postWebMessage(webView, WebMessageCompat("capturePort", destPort), Uri.EMPTY)

    }
}