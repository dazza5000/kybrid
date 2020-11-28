package com.whereisdarran.kybrid.core

import com.whereisdarran.kybrid.PlatformWebView

object PluginRegistry {
    private val registeredPlugins: MutableList<MultiPlatformPlugin<*>> = emptyList<MultiPlatformPlugin<*>>().toMutableList()

    fun registerPlugin(multiPlatformPlugin: MultiPlatformPlugin<*>) {
        registeredPlugins.add(multiPlatformPlugin)
    }

    fun initializePlugins(platformWebView: PlatformWebView) {
        registeredPlugins.forEach {
            it.initialize(platformWebView)
        }
    }

    fun clearPlugins() {
        registeredPlugins.clear()
    }
}