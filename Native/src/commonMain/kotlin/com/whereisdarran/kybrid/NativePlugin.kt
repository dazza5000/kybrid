package com.whereisdarran.kybrid

expect class NativePlugin {
    fun initialize(pluginConfig: PluginConfig)
    fun sendMessage(message: String)
    fun setIncomingMessageCallback(incomingMessageCallback: IncomingMessageCallback)
}

interface IncomingMessageCallback {
    fun onIncomingMessageCallback(message: String)
}

expect class KybridWebView {
    fun loadKybridUrl(url: String)
}