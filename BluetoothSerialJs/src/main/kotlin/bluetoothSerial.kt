import com.fivestars.cordovaalternativepattern.KEY_MAC_ADDRESS
import com.fivestars.cordovaalternativepattern.model.Action
import model.JavascriptMessage
import kotlinx.serialization.*
import kotlinx.serialization.json.*

object BluetoothSerial {
    @JsName("connect")
    fun connect(macAddress: String, success: () -> Unit, failure: () -> Unit) {

        val message = JavascriptMessage(action = Action.CONNECT, data = mapOf(
            KEY_MAC_ADDRESS to macAddress))

        val json = Json(JsonConfiguration.Stable)
        // serializing objects
        val jsonData = json.stringify(JavascriptMessage.serializer(), message)

        outputPort.postMessage(jsonData)
    }

    fun disconnect() {
        outputPort.postMessage(JavascriptMessage(Action.DISCONNECT, null))
    }

    fun send(data: ByteArray, success: () -> Unit, failure: () -> Unit) { }

    fun listen(success: () -> Unit, failure: () -> Unit) {}

    fun getAddress(success: () -> Unit, failure: () -> Unit) {}

    fun registerOnDataCallback(success: () -> Unit) {

    }

    fun registerOnConnectCallback(success: () -> Unit) {

    }

    fun registerOnCloseCallback(success: () -> Unit) {

    }
}

@Serializable
data class MessageEvent(val data: JavascriptMessage)