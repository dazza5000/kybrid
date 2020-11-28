package com.whereisdarran.kybrid.plugin.deviceinfo

actual class DeviceInfoProvider {
    actual fun getDeviceInfo(): DeviceInfo {
        return DeviceInfo(android.os.Build.MODEL, android.os.Build.VERSION.SDK_INT.toString())
    }
}