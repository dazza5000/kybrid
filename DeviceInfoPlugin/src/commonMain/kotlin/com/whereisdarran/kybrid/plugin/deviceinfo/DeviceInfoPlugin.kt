package com.whereisdarran.kybrid.plugin.deviceinfo

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

class DeviceInfoPlugin: com.whereisdarran.kybrid.core.MultiPlatformPlugin<Action>(Config) {
    override val actionSerializer: KSerializer<Action>
        get() = Action.serializer()

    override fun handleAction(action: Action, data: String?, callbackUid: String?) {

    }
}


@Serializable
enum class Action {
    GET_DEVICE_INFO
}

@Serializable
data class DeviceInfo(val model: String, val osVersion: String)

object Config : com.whereisdarran.kybrid.PluginConfig {
    override val PORT = "OkPayPlugin"
    override val TAG = "OkPayPlugin"
}
