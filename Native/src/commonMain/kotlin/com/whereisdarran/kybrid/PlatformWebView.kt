package com.whereisdarran.kybrid

expect class PlatformWebView {
    fun initialize(pluginConfig: PluginConfig)
    fun sendMessage(message: String)
    fun setIncomingMessageCallback(incomingMessageCallback: IncomingMessageCallback)
}

interface IncomingMessageCallback {
    fun onIncomingMessageCallback(message: String)
}

expect class KybridView {
    fun loadKybridUrl(url: String)
}