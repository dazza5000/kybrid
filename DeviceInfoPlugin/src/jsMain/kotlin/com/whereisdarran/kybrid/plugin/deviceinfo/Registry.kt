package com.whereisdarran.kybrid.plugin.deviceinfo

object Registry {
    init {
        com.whereisdarran.kybrid.core.js.PluginRegistry.registerPlugin(DeviceInfoInterface)
    }
}