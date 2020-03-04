import com.fivestars.cordovaalternativepattern.KEY_MAC_ADDRESS
import com.fivestars.cordovaalternativepattern.model.Action
import model.JavascriptMessage

object BluetoothSerial {
    fun connect(macAddress: String, success: () -> Unit, failure: () -> Unit) {
        val message = JavascriptMessage(action = Action.CONNECT, data = mapOf(
            KEY_MAC_ADDRESS to macAddress))
        outputPort.postMessage(message)
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