package com.whereisdarran.kybrid.androidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.whereisdarran.kybrid.KybridWebView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<KybridWebView>(R.id.kybridWebview).loadKybridUrl("https://www.duckduckgo.com")
    }
}
