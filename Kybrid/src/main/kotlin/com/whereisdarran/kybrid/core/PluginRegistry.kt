package com.whereisdarran.kybrid.core

import com.whereisdarran.kybrid.PlatformWebView

object PluginRegistry {
    private val registeredPlugins: MutableList<PluginInterface<*>> = emptyList<PluginInterface<*>>().toMutableList()

    fun registerPlugin(multiPlatformPlugin: PluginInterface<*>) {
        registeredPlugins.add(multiPlatformPlugin)
    }

    fun initializePlugins() {
        registeredPlugins.forEach {
            it.initialize()
        }
    }

    fun clearPlugins() {
        registeredPlugins.clear()
    }
}