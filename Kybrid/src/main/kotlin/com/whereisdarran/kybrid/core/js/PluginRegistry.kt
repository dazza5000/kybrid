package com.whereisdarran.kybrid.core.js

object PluginRegistry {

        private val registeredPlugins: MutableList<PluginInterface<*>> = emptyList<PluginInterface<*>>().toMutableList()

        final fun registerPlugin(pluginInterface: PluginInterface<*>) {
            registeredPlugins.add(pluginInterface)
        }

        final fun clearPlugins() {
            registeredPlugins.clear()
        }
}