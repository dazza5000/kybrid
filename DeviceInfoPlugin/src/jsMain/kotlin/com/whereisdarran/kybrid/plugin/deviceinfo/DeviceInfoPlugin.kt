package com.whereisdarran.kybrid.plugin.deviceinfo

import com.whereisdarran.kybrid.PluginConfig
import com.whereisdarran.kybrid.core.PluginInterface
import com.whereisdarran.kybrid.util.messaging.CallbackUtil
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlin.js.Promise

@ExperimentalJsExport
@JsName("DeviceInfoInterface")
object DeviceInfoInterface: PluginInterface<Action>(CallbackUtil(), Config) {

    override val actionSerializer: KSerializer<Action>
        get() = Action.serializer()

    init {
        console.log("${this.config.TAG}: Starting Initialization")
        initialize().then { print(it) }
    }

    @JsName("getDeviceInfo")
    fun getDeviceInfo(): Promise<DeviceInfo> = callNative(
        Action.GET_DEVICE_INFO,
        null,
        DeviceInfo.serializer()
    )
}