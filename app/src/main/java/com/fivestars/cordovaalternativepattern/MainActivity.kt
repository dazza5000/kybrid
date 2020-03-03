package com.fivestars.cordovaalternativepattern

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import androidx.webkit.WebMessageCompat
import androidx.webkit.WebViewCompat

class MainActivity : AppCompatActivity() {

    private lateinit var rootView: View
    private lateinit var webViewCallbacks: WebView
    private lateinit var btnSendMessage: Button
    private lateinit var txtSendMessage: EditText
    private lateinit var webViewConfig: WebViewConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webViewCallbacks = rootView.findViewById(R.id.webViewCallbacks)
        btnSendMessage = rootView.findViewById(R.id.btnSendMessage)
        txtSendMessage = rootView.findViewById(R.id.txtSendMessage)
        webViewConfig = WebViewConfig(webViewCallbacks)
        btnSendMessage.setOnClickListener {
            WebViewCompat.postWebMessage(webViewCallbacks, WebMessageCompat(txtSendMessage.text.toString()), Uri.EMPTY)
        }

    }
}
