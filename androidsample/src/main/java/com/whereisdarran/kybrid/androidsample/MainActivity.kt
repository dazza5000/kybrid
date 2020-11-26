package com.whereisdarran.kybrid.androidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.whereisdarran.kybrid.KybridWebView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val kybridWebView = KybridWebView(this)

        findViewById<ConstraintLayout>(R.id.root_constraint_layout).addView(kybridWebView)

        kybridWebView.loadUrl("www.google.com")
    }
}
