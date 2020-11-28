package com.whereisdarran.kybrid

actual class PlatformWebView {
    actual fun initialize(pluginConfig: PluginConfig) {
        throw NotImplementedError()
    }

    actual fun sendMessage(message: String) {
        throw NotImplementedError()
    }

    actual fun setIncomingMessageCallback(incomingMessageCallback: IncomingMessageCallback) {
        throw NotImplementedError()
    }
}
