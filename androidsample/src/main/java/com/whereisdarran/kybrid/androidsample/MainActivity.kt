package com.whereisdarran.kybrid.androidsample

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.whereisdarran.kybrid.KybridView
import com.whereisdarran.kybrid.plugin.deviceinfo.Action
import com.whereisdarran.kybrid.plugin.deviceinfo.DeviceInfoPlugin
import com.whereisdarran.kybrid.plugin.deviceinfo.DeviceInfoProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)

        val deviceInfoPlugin = DeviceInfoPlugin(DeviceInfoProvider())

        com.whereisdarran.kybrid.core.PluginRegistry
            .registerPlugin(deviceInfoPlugin)

        findViewById<KybridView>(R.id.kybridWebview).loadKybridUrl("file:///android_asset/index.html")

    }
}
