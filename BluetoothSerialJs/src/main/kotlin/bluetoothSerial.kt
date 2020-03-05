import com.fivestars.cordovaalternativepattern.KEY_MAC_ADDRESS
import com.fivestars.cordovaalternativepattern.KEY_SEND_DATA
import com.fivestars.cordovaalternativepattern.model.Action
import com.fivestars.cordovaalternativepattern.model.CallbackId
import com.fivestars.cordovaalternativepattern.model.NativeDataMessage
import kotlinx.serialization.json.*
import model.JavascriptMessage
import org.w3c.dom.MessageEvent
import kotlin.browser.window

object BluetoothSerial {
    private val callbacks = mutableMapOf<CallbackId, () -> Unit>()
    private val json = Json(JsonConfiguration.Stable)
    private var onDataCallback: ((String) -> Unit)? = null

    @JsName("connect")
    fun connect(macAddress: String, success: () -> Unit, failure: () -> Unit) {

        callbacks[CallbackId.CONNECT_SUCCESS] = success
        callbacks[CallbackId.CONNECT_FAILURE] = failure

        window.addEventListener("message", {
            val event = it as MessageEvent

            val dataMessage = json.parse(NativeDataMessage.serializer(), it.data.toString())

            dataMessage.callbackId.run {
                callbacks[this]?.invoke()

                callbacks.remove(CallbackId.CONNECT_SUCCESS)
                callbacks.remove(CallbackId.CONNECT_FAILURE)
            }

        }, false)

        val message = JavascriptMessage(Action.CONNECT, CallbackId.CONNECT_SUCCESS, CallbackId.CONNECT_FAILURE, mapOf(
            KEY_MAC_ADDRESS to macAddress))

        val jsonData = json.stringify(JavascriptMessage.serializer(), message)

        outputPort.postMessage(jsonData)
    }

    fun disconnect(success: () -> Unit, failure: () -> Unit) {
        outputPort.postMessage(JavascriptMessage(Action.DISCONNECT, null, null, null))
    }

    @ExperimentalStdlibApi
    @JsName("send")
    fun send(data: String, success: () -> Unit, failure: () -> Unit) {

        callbacks[CallbackId.SEND_SUCCESS] = success
        callbacks[CallbackId.SEND_FAILURE] = failure

        window.addEventListener("message", {
            val event = it as MessageEvent

            val dataMessage = json.parse(NativeDataMessage.serializer(), it.data.toString())

            dataMessage.callbackId.run {
                callbacks[this]?.invoke()

                callbacks.remove(CallbackId.SEND_SUCCESS)
                callbacks.remove(CallbackId.SEND_FAILURE)
            }

        }, false)

        val message = JavascriptMessage(Action.SEND, CallbackId.SEND_SUCCESS, CallbackId.SEND_FAILURE, mapOf(
            KEY_SEND_DATA to data))

        val jsonData = json.stringify(JavascriptMessage.serializer(), message)

        outputPort.postMessage(jsonData)
    }

    fun listen(success: () -> Unit, failure: () -> Unit) {}

    fun getAddress(success: () -> Unit, failure: () -> Unit) {}

    @JsName("registerOnDataCallback")
    fun registerOnDataCallback(success: (data: String) -> Unit) {
        console.log("Registering the onDataCallback")

        onDataCallback = success;

        window.addEventListener("message", {

            it as MessageEvent
            val dataMessage = json.parse(NativeDataMessage.serializer(), it.data.toString())

            dataMessage.data?.run {
                console.log("data message is: ${dataMessage.data}")
            }
            dataMessage.data?.run {
                onDataCallback?.invoke(this)
            }

        }, false)

        val message = JavascriptMessage(Action.REGISTER_DATA_CALLBACK, null, null, null)

        val jsonData = json.stringify(JavascriptMessage.serializer(), message)

        outputPort.postMessage(jsonData)
    }

    @JsName("registerOnConnectCallback")
    fun registerOnConnectCallback(onConnectCallback: () -> Unit) {
        callbacks[CallbackId.ON_CONNECT_CALLBACK] = onConnectCallback
    }

    @JsName("registerOnCloseCallback")
    fun registerOnCloseCallback(onCloseCallback: () -> Unit) {
        callbacks[CallbackId.ON_CLOSE_CALLBACK] = onCloseCallback
    }
}