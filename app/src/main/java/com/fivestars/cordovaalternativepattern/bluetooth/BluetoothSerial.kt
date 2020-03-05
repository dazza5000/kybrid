package com.fivestars.cordovaalternativepattern.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.WebView
import androidx.webkit.WebMessageCompat
import androidx.webkit.WebViewCompat
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerialService.ClosedCallback
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerialService.ConnectedCallback
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerialService.DataCallback
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerialService.STATE_CONNECTED
import com.fivestars.cordovaalternativepattern.model.CallbackId
import com.fivestars.cordovaalternativepattern.model.NativeDataMessage
import org.json.JSONException
import java.lang.Exception
import java.lang.reflect.Field
import kotlinx.serialization.json.*
import kotlin.text.Charsets.UTF_8


object BluetoothSerial {

    private const val TAG = "BluetoothSerial"

    private var dataAvailableCallbackId: CallbackId? = null
    private var connectCallbackId: CallbackId? = null
    private var disconnectCallbackId: CallbackId? = null
    public var messageHandler: MessageHandler? = null
    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val json = Json(JsonConfiguration.Stable)


    fun listen(callbackContext: Any?) {
        if (BluetoothSerialService.state == STATE_CONNECTED) {
            //callbackContext.error("Already connected")
        } else {
            enableBluetoothIfNecessary()
            try {
                BluetoothSerialService.start()
                //callbackContext.success()
            } catch (e: Exception) {
                //callbackContext.error(e.toString())
            }
        }
    }

    fun onDestroy() {
        BluetoothSerialService.stop()
    }

    @SuppressLint("RequiresFeature")
    @Throws(JSONException::class)
    fun connect(macAddress: String, successCallbackId: CallbackId?, failureCallbackId: CallbackId?, webViewCompat: WebView) {
        enableBluetoothIfNecessary()
        val device = bluetoothAdapter.getRemoteDevice(macAddress)
        if (device != null) {

            BluetoothSerialService.connect(device)

            successCallbackId?.run {
                val nativeDataMessage = NativeDataMessage(this, null)
                val jsonData = json.stringify(NativeDataMessage.serializer(), nativeDataMessage)
                WebViewCompat.postWebMessage(webViewCompat, WebMessageCompat(jsonData), Uri.EMPTY)
            }
        } else {
            failureCallbackId?.run {
                val nativeDataMessage = NativeDataMessage(this, null)
                val json = Json(JsonConfiguration.Stable)
                val jsonData = json.stringify(NativeDataMessage.serializer(), nativeDataMessage)
                WebViewCompat.postWebMessage(webViewCompat, WebMessageCompat(jsonData), Uri.EMPTY)
            }
        }
    }

    fun write(sendData: String, successCallbackId: CallbackId?, failureCallbackId: CallbackId?, webViewCompat: WebView) {

        try {
            BluetoothSerialService.write(sendData.toByteArray())
            successCallbackId?.run {
                val nativeDataMessage = NativeDataMessage(this, null)
                val json = Json(JsonConfiguration.Stable)
                val jsonData = json.stringify(NativeDataMessage.serializer(), nativeDataMessage)
                WebViewCompat.postWebMessage(webViewCompat, WebMessageCompat(jsonData), Uri.EMPTY)
            }
        } catch (e: Exception) {
            failureCallbackId?.run {
                val nativeDataMessage = NativeDataMessage(this, e.toString())
                val json = Json(JsonConfiguration.Stable)
                val jsonData = json.stringify(NativeDataMessage.serializer(), nativeDataMessage)
                WebViewCompat.postWebMessage(webViewCompat, WebMessageCompat(jsonData), Uri.EMPTY)
            }
        }
    }

    fun registerConnectCallback(callbackId: CallbackId, webView: WebView) {
        this.connectCallbackId = callbackId
        BluetoothSerialService.registerConnectedCallback(object : ConnectedCallback {
            override fun connected() {
                notifyConnectionSuccess()
            }
        })
    }

    fun registerDisconnectCallback(callbackId: CallbackId, webView: WebView) {
        this.disconnectCallbackId = callbackId
        BluetoothSerialService.registerClosedCallback(object : ClosedCallback {
            override fun closed() {
                notifyConnectionLost()
            }
        })
    }

    fun registerOnDataCallback(callbackId: CallbackId, webView: WebView) {
        this.dataAvailableCallbackId = callbackId
        BluetoothSerialService.registerDataCallback(object : DataCallback {
            override fun onData(data: ByteArray) {
                val data1 = data.toString(UTF_8)
                if (data1.isNotEmpty()) {
                    dataAvailableCallbackId?.run {
                        val nativeDataMessage = NativeDataMessage(this, data1)
                        val json = Json(JsonConfiguration.Stable)
                        val jsonData = json.stringify(NativeDataMessage.serializer(), nativeDataMessage)
                        messageHandler.sendMessage(jsonData)
                    }
                }
            }
        })
    }

    fun disconnect() {
        try {
            BluetoothSerialService.stop()
            //callbackContext.success()
        } catch (e: Exception) {
            //callbackContext.error(e.toString())
        }
    }

    fun getAddress() {
        val macAddress = getBluetoothMacAddress()

        macAddress?.run {
            //callbackContext.success(this)
        } ?: callbackContext.error("Unable to determine Bluetooth MAC address")
    }

    private fun notifyConnectionSuccess() {
        connectCallbackId?.run {
            val nativeDataMessage = NativeDataMessage(this, null)
            messageHandler?.sendMessage(json.stringify(NativeDataMessage.serializer(), nativeDataMessage))
        }
    }

    private fun notifyConnectionLost() {
        disconnectCallbackId?.run {
            val nativeDataMessage = NativeDataMessage(this, null)
            messageHandler?.sendMessage(json.stringify(NativeDataMessage.serializer(), nativeDataMessage))
        }
    }

    private fun getBluetoothMacAddress(): String? {
        var bluetoothMacAddress: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val serviceField: Field? = bluetoothAdapter?.javaClass?.getDeclaredField("mService")
                serviceField?.isAccessible = true
                val btManagerService: Any? = serviceField?.get(bluetoothAdapter)
                btManagerService?.run {
                    bluetoothMacAddress =
                            javaClass.getMethod("getAddress").invoke(btManagerService) as String
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to retrieve Bluetooth MAC Address: $e")
            }
        } else {
            bluetoothMacAddress = bluetoothAdapter?.address
        }
        return bluetoothMacAddress
    }

    private fun enableBluetoothIfNecessary() {
        if (!bluetoothAdapter.isEnabled) {
            bluetoothAdapter.enable()
        }
    }

    interface MessageHandler {
        fun sendMessage(message: String)
    }
}
