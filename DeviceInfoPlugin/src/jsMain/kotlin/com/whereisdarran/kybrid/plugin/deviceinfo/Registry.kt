package com.whereisdarran.kybrid.plugin.deviceinfo

import kotlinx.browser.window

object Registry {
    init {
        com.whereisdarran.kybrid.core.js.PluginRegistry.registerPlugin(DeviceInfoInterface)
    }
}