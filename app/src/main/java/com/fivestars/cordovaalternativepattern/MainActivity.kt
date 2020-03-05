package com.fivestars.cordovaalternativepattern

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import androidx.webkit.WebMessageCompat
import androidx.webkit.WebViewCompat
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerial
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var webViewConfig: WebViewConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    @SuppressLint("RequiresFeature")
    override fun onStart() {
        super.onStart()
        webViewConfig = WebViewConfig(webView)
        btnSendMessage.setOnClickListener {
            BluetoothSerial.write("asdf".toByteArray())
        }
    }
}
