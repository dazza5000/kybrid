package com.whereisdarran.kybrid.util.messaging

import com.whereisdarran.kybrid.model.message.NativeMessage
import com.whereisdarran.kybrid.util.uuid
import kotlinx.serialization.json.Json

class CallbackUtil<Action> {
    data class CallbackEntry<Action>(
        val action: Action,
        val success: (dynamic) -> Unit,
        val failure: ((dynamic) -> Unit)?
    )

    val persistentCallbacks = mutableMapOf<Action, CallbackEntry<Action>>()
    private val singleCallbacks = mutableMapOf<String, CallbackEntry<Action>>()

    private val json = Json

    fun registerPersistentCallback(
        action: Action,
        success: (dynamic) -> Unit,
        failure: ((dynamic) -> Unit)? = null
    ) {
        persistentCallbacks[action] = CallbackEntry(action, success, failure)
    }

    @ExperimentalUnsignedTypes
    fun registerSingleCallback(
        action: Action,
        success: (dynamic) -> Unit,
        failure: ((dynamic) -> Unit)? = null
    ): String {
        val uuid = uuid()
        singleCallbacks[uuid] = CallbackEntry(action, success, failure)
        return uuid
    }

    fun invokeCallback(nativeMessage: NativeMessage<Action>) {
        // invoke persistent callbacks
        persistentCallbacks[nativeMessage.action]?.let {
            when (nativeMessage.success) {
                true -> it.success(nativeMessage.data)
                false -> it.failure?.invoke(nativeMessage.data)
            }
        }

        nativeMessage.callbackUid ?: return

        singleCallbacks[nativeMessage.callbackUid]?.let {
            when (nativeMessage.success) {
                true -> it.success(nativeMessage.data)
                false -> it.failure?.invoke(nativeMessage.data)
            }

            singleCallbacks.remove(nativeMessage.callbackUid)
        }
    }
}
