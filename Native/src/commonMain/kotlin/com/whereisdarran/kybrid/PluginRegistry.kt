package com.whereisdarran.kybrid

object PluginRegistry {
    private val registeredPlugins: MutableList<MultiPlatformPlugin<*>> = emptyList<MultiPlatformPlugin<*>>().toMutableList()

    fun registerPlugin(multiPlatformPlugin: MultiPlatformPlugin<*>) {
        registeredPlugins.add(multiPlatformPlugin)
    }

    fun initializePlugins() {
        registeredPlugins.forEach {

        }
    }

    fun clearPlugins() {
        registeredPlugins.clear()
    }
}