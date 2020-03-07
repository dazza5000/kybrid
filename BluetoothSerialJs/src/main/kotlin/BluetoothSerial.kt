package com.fivestars.bluetooth

import com.fivestars.cordovaalternativepattern.KEY_CAPTURE_PORT
import com.fivestars.cordovaalternativepattern.KEY_MAC_ADDRESS
import com.fivestars.cordovaalternativepattern.KEY_SEND_DATA
import com.fivestars.cordovaalternativepattern.model.Action
import com.fivestars.cordovaalternativepattern.model.Callback
import com.fivestars.cordovaalternativepattern.model.message.NativeDataMessage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import com.fivestars.cordovaalternativepattern.model.message.JavascriptMessage
import org.w3c.dom.MessageChannel
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import kotlin.browser.window

object BluetoothSerial {

    private val channel = MessageChannel()
    private var inputPort = channel.port1
    private var outputPort = channel.port2

    private val callbacks = mutableMapOf<Callback, () -> Unit>()
    private val json = Json(JsonConfiguration.Stable)
    private var onDataCallback: ((String) -> Unit)? = null

    @JsName("listen")
    fun listen(success: () -> Unit, failure: () -> Unit) {
        registerCallbacks(Callback.LISTEN_SUCCESS, Callback.LISTEN_FAILURE, success, failure, true)
    }

    @JsName("connect")
    fun connect(macAddress: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        registerCallbacks(
            Callback.CONNECT_SUCCESS,
            Callback.CONNECT_FAILURE,
            onSuccess,
            onFailure,
            true
        )

        val message =
            JavascriptMessage(
                Action.CONNECT, Callback.CONNECT_SUCCESS, Callback.CONNECT_FAILURE, mapOf(
                    KEY_MAC_ADDRESS to macAddress
                )
            )

        sendMessageToNative(message)
    }

    @JsName("disconnect")
    fun disconnect(success: () -> Unit, failure: () -> Unit) {
        registerCallbacks(
            Callback.DISCONNECT_SUCCESS,
            Callback.DISCONNECT_FAILURE,
            success,
            failure,
            true
        )
        sendMessageToNative(
            JavascriptMessage(
                Action.DISCONNECT,
                null,
                null,
                null
            )
        )
    }

    @JsName("send")
    fun send(data: String, success: () -> Unit, failure: () -> Unit) {
        registerCallbacks(Callback.SEND_SUCCESS, Callback.SEND_FAILURE, success, failure, true)

        val javascriptMessage =
            JavascriptMessage(
                Action.SEND, Callback.SEND_SUCCESS, Callback.SEND_FAILURE, mapOf(
                    KEY_SEND_DATA to data
                )
            )

        sendMessageToNative(javascriptMessage);
    }

    @JsName("registerOnDataCallback")
    fun registerOnDataCallback(success: (data: String) -> Unit) {
        onDataCallback = success;

        window.addEventListener("message", {
            it as MessageEvent
            val dataMessage = json.parse(NativeDataMessage.serializer(), it.data.toString())

            dataMessage.data?.run {
                onDataCallback?.invoke(this)
            }

        }, false)

        val javascriptMessage =
            JavascriptMessage(
                Action.REGISTER_DATA_CALLBACK,
                null,
                null,
                null
            )
        sendMessageToNative(javascriptMessage)
    }

    @JsName("registerOnConnectCallback")
    fun registerOnConnectCallback(onConnectCallback: () -> Unit) {
        registerCallbacks(Callback.ON_CONNECT_CALLBACK, null, onConnectCallback, null, false)
        val javascriptMessage =
            JavascriptMessage(
                Action.REGISTER_CONNECT_CALLBACK,
                null,
                null,
                null
            )
        sendMessageToNative(javascriptMessage)
    }

    @JsName("registerOnCloseCallback")
    fun registerOnCloseCallback(onCloseCallback: () -> Unit) {
        registerCallbacks(Callback.ON_CLOSE_CALLBACK, null, onCloseCallback, null, false)
        val javascriptMessage =
            JavascriptMessage(
                Action.REGISTER_DISCONNECT_CALLBACK,
                null,
                null,
                null
            )
        sendMessageToNative(javascriptMessage)
    }

    @JsName("getAddress")
    fun getAddress(success: () -> Unit, failure: () -> Unit) {
        registerCallbacks(
            Callback.GET_ADDRESS_SUCCESS,
            Callback.GET_ADDRESS_FAILURE,
            success,
            failure,
            true
        )
    }

    private fun registerCallbacks(
        successCallback: Callback,
        failureCallback: Callback?,
        success: () -> Unit,
        failure: (() -> Unit)?,
        removeCallbacks: Boolean
    ) {

        // Assign callbacks for later use
        callbacks[successCallback] = success
        if (failureCallback != null && failure != null) {
            callbacks[failureCallback] = failure
        }

        val listener = object: EventListener {
            override fun handleEvent(event: Event) {
                    val dataMessage =
                        json.parse(NativeDataMessage.serializer(), (event as MessageEvent).data.toString())

                    dataMessage.callback.run {
                        callbacks[this]?.invoke()

                        // Remove callbacks if we need to
                        if (removeCallbacks) {
                            callbacks.remove(successCallback)
                            callbacks.remove(failureCallback)
                        }
                    }
            }
        }

        // Setup listener for native message response
        window.addEventListener("message", listener, false)
    }

    private fun sendMessageToNative(javascriptMessage: JavascriptMessage) {
        val jsonData = json.stringify(JavascriptMessage.serializer(), javascriptMessage)
        outputPort.postMessage(jsonData)
    }

    @JsName("configureChannel")
    fun configureChannel() {
        console.log("Configuring channel")
        window.addEventListener("message", {

            console.log("Got event; $it")

            val event = it as MessageEvent

            if (event.data != KEY_CAPTURE_PORT) {
                console.log("event.data: ${event.data}")
                inputPort.postMessage(event.data)
            } else if (event.data == KEY_CAPTURE_PORT) {
                console.log("assigning captured port")
                outputPort = event.ports[0]
            }
        }, false)

        inputPort.start()
        outputPort.start()
    }

    @JsName("postAlert")
    fun postAlert() {
        window.alert("yolo")
    }
}