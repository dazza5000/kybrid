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



/**
 * PhoneGap Plugin for Serial Communication over Bluetooth
 */
object BluetoothSerial {
    private var connectCallback: Any? = null
    private var closeCallback: Any? = null
    private var dataAvailableCallbackId: CallbackId? = null
    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    @Throws(JSONException::class)
    fun execute(action: String, args: String, callbackContext: Any?): Boolean {
        Log.d(TAG, "action = $action")
        var validAction = true
        when (action) {
            DISCONNECT -> {
                try {
                    BluetoothSerialService.stop()
                    //callbackContext.success()
                } catch (e: Exception) {
                    //callbackContext.error(e.toString())
                }
            }
            LISTEN -> {
                listen(callbackContext)
            }
            GET_ADDRESS -> {
                val macAddress = getBluetoothMacAddress()

//                macAddress?.run {
//                    //callbackContext.success(this)
//                } ?: callbackContext.error("Unable to determine Bluetooth MAC address")
            }
            REGISTER_CONNECT_CALLBACK -> {
                connectCallback = callbackContext
                BluetoothSerialService.registerConnectedCallback(object : ConnectedCallback {
                    override fun connected() {
                        notifyConnectionSuccess()
                    }
                })
            }
            REGISTER_CLOSE_CALLBACK -> {
                closeCallback = callbackContext
                BluetoothSerialService.registerClosedCallback(object : ClosedCallback {
                    override fun closed() {
                        notifyConnectionLost()
                    }
                })
            }
            else -> {
                validAction = false
            }
        }
        return validAction
    }

    private fun listen(callbackContext: Any?) {
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

    private fun enableBluetoothIfNecessary() {
        if (!bluetoothAdapter.isEnabled) {
            bluetoothAdapter.enable()
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
                val json = Json(JsonConfiguration.Stable)
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
                val nativeDataMessage = NativeDataMessage(this, e.toString().toByteArray())
                val json = Json(JsonConfiguration.Stable)
                val jsonData = json.stringify(NativeDataMessage.serializer(), nativeDataMessage)
                WebViewCompat.postWebMessage(webViewCompat, WebMessageCompat(jsonData), Uri.EMPTY)
            }
        }
    }

    fun registerOnDataCallback(callbackId: CallbackId, webView: WebView) {
        this.dataAvailableCallbackId = callbackId
        BluetoothSerialService.registerDataCallback(object : DataCallback {
            override fun onData(data: ByteArray) {
                sendRawDataToSubscriber(data, webView)
            }
        })
    }

    private fun notifyConnectionLost() {
//        closeCallback?.success()
    }

    private fun notifyConnectionSuccess() {
//        val result = PluginResult(PluginResult.Status.OK)
//        result.keepCallback = true
//        connectCallback?.sendPluginResult(result)
    }

    @SuppressLint("RequiresFeature")
    private fun sendRawDataToSubscriber(data: ByteArray?, webView: WebView) {
        if (data != null && data.isNotEmpty()) {
            dataAvailableCallbackId?.run {
                val nativeDataMessage = NativeDataMessage(this, data)
                val json = Json(JsonConfiguration.Stable)
                val jsonData = json.stringify(NativeDataMessage.serializer(), nativeDataMessage)
                WebViewCompat.postWebMessage(webView, WebMessageCompat(jsonData), Uri.EMPTY)
            }
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


        private const val LISTEN = "listen"
        private const val DISCONNECT = "disconnect"
        private const val GET_ADDRESS = "getAddress"
        private const val REGISTER_CONNECT_CALLBACK = "registerConnectCallback"
        private const val REGISTER_CLOSE_CALLBACK = "registerCloseCallback"

        // Debugging
        private const val TAG = "BluetoothSerial"
}
