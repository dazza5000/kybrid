package com.whereisdarran.kybrid.plugin.deviceinfo

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable


class DeviceInfoPlugin(val deviceInfoProvider: DeviceInfoProvider): com.whereisdarran.kybrid.core.MultiPlatformPlugin<Action>(Config) {
    override val actionSerializer: KSerializer<Action>
        get() = Action.serializer()

    override fun handleAction(action: Action, data: String?, callbackUid: String?) {
        when(action) {
             Action.GET_DEVICE_INFO -> {
                 sendSuccess(Action.GET_DEVICE_INFO, json.encodeToString(DeviceInfo.serializer(), deviceInfoProvider.getDeviceInfo()), callbackUid)
             }
        }
    }
}

@Serializable
enum class Action {
    GET_DEVICE_INFO
}

@Serializable
data class DeviceInfo(val model: String, val osVersion: String)

public object Config : com.whereisdarran.kybrid.PluginConfig {
    override val PORT = "DeviceInfoPlugin"
    override val TAG = "DeviceInfoPlugin"
}
