package com.whereisdarran.kybrid

import com.whereisdarran.instore.message.JavascriptMessage
import com.whereisdarran.instore.message.NativeMessage
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract class MultiPlatformPlugin<ActionT>(private val config: PluginConfig) {
    private var nativePlugin: NativePlugin? = null
    protected val json = Json

    // Plugins must override this to expose their Action type's serializer, used
    // by serialize() and deserialize() below.
    protected abstract val actionSerializer: KSerializer<ActionT>
    private val incomingMessageCallback: IncomingMessageCallback = object : IncomingMessageCallback {
        override fun onIncomingMessageCallback(message: String) {
            try {
                val javascriptMessage = deserialize(message)
                handleAction(javascriptMessage.action, javascriptMessage.data, javascriptMessage.callbackUid)
            } catch (e: Exception) {
                reportError(e)
            }
        }
    }

    abstract fun handleAction(action: ActionT, data: String?, callbackUid: String?)

    open fun reportError(exception: Exception) { }

    open fun cleanUp() {}

    fun initialize(nativePlugin: NativePlugin) {
        this.nativePlugin = nativePlugin
        nativePlugin.initialize(config)
        nativePlugin.setIncomingMessageCallback(incomingMessageCallback)
        postInitialize()
    }

    open fun postInitialize() {}

    protected fun sendSuccess(action: ActionT, data: String? = null, callbackUid: String? = null) {
        sendMessage(
            NativeMessage(action, true, data, callbackUid)
        )
    }

    protected fun sendFailure(action: ActionT, data: String, callbackUid: String? = null) {
        sendMessage(
            NativeMessage(action, false, data, callbackUid)
        )
    }

    fun sendMessage(message: NativeMessage<ActionT>) {
        val jsonData = serialize(message)

        nativePlugin?.sendMessage(jsonData)
    }

    /**
     * Serialize a NativeMessage to json, to send to the web layer.
     */
    fun serialize(msg: NativeMessage<ActionT>): String {
        return json.encodeToString(NativeMessage.serializer(actionSerializer), msg)
    }

    /**
     * Deserialize a message received from the web layer.
     */
    fun deserialize(jsonString: String): JavascriptMessage<ActionT> {
        return json.decodeFromString(JavascriptMessage.serializer(actionSerializer), jsonString)
    }
}
