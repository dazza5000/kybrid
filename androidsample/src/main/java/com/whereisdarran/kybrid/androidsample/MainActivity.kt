package com.whereisdarran.kybrid.androidsample

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.whereisdarran.kybrid.KybridView
import com.whereisdarran.kybrid.plugin.deviceinfo.Action
import com.whereisdarran.kybrid.plugin.deviceinfo.DeviceInfoPlugin

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deviceInfoPlugin = DeviceInfoPlugin(Build.MODEL, Build.VERSION.SDK_INT.toString())

        com.whereisdarran.kybrid.core.PluginRegistry
            .registerPlugin(deviceInfoPlugin)

        findViewById<KybridView>(R.id.kybridWebview).loadKybridUrl("file:///android_asset/index.html")

        deviceInfoPlugin.handleAction(Action.GET_DEVICE_INFO, null, null)
    }
}
