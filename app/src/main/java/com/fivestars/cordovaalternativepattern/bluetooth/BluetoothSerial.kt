package com.fivestars.cordovaalternativepattern.bluetooth

import android.bluetooth.BluetoothAdapter
import android.os.Build
import android.util.Log
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerialService.ClosedCallback
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerialService.ConnectedCallback
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerialService.DataCallback
import com.fivestars.cordovaalternativepattern.bluetooth.BluetoothSerialService.STATE_CONNECTED
import com.fivestars.cordovaalternativepattern.model.Callback
import com.fivestars.cordovaalternativepattern.model.message.NativeDataMessage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.lang.reflect.Field
import kotlin.text.Charsets.UTF_8


object BluetoothSerial {

    private const val TAG = "BluetoothSerial"

    private var dataAvailableCallback: Callback? = null
    private var connectCallback: Callback? = null
    private var disconnectCallback: Callback? = null
    internal var messageHandler: MessageHandler? = null

    fun listen(
        successCallback: Callback?,
        failureCallback: Callback?
    ) {
        if (BluetoothSerialService.state == STATE_CONNECTED) {
            sendFailure(failureCallback, Exception("Already Connected"))
        } else {
            enableBluetoothIfNecessary()
            try {
                BluetoothSerialService.start()
                sendSuccess(successCallback)
            } catch (e: Exception) {
                sendFailure(failureCallback, e)
            }
        }
    }

    fun connect(
        macAddress: String,
        successCallback: Callback?,
        failureCallback: Callback?
    ) {
        enableBluetoothIfNecessary()
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val device = bluetoothAdapter.getRemoteDevice(macAddress)
        if (device != null) {
            BluetoothSerialService.connect(device)

            successCallback?.run {
                val nativeDataMessage =
                    NativeDataMessage(
                        this,
                        null
                    )
                messageHandler?.sendMessage(nativeDataMessage)
            }
        } else {
            sendFailure(failureCallback)
        }
    }

    fun disconnect(successCallback: Callback?, failureCallback: Callback?) {
        try {
            BluetoothSerialService.stop()
            sendSuccess(successCallback)
        } catch (e: Exception) {
            sendFailure(failureCallback, e)
        }
    }


    fun write(
        sendData: String,
        successCallback: Callback?,
        failureCallback: Callback?
    ) {

        try {
            BluetoothSerialService.write(sendData.toByteArray())
            sendSuccess(successCallback)
        } catch (e: Exception) {
            sendFailure(failureCallback, e)
        }
    }

    private fun sendFailure(
        failureCallback: Callback?,
        e: Exception? = null
    ) {
        failureCallback?.run {
            val nativeDataMessage =
                NativeDataMessage(
                    this,
                    e?.toString()
                )
            messageHandler?.sendMessage(nativeDataMessage)
        }
    }

    private fun sendSuccess(successCallback: Callback?, data: String? = null) {
        successCallback?.run {
            val nativeDataMessage =
                NativeDataMessage(
                    this,
                    data
                )
            messageHandler?.sendMessage(nativeDataMessage)
        }
    }

    fun registerConnectCallback(callback: Callback) {
        this.connectCallback = callback
        BluetoothSerialService.registerConnectedCallback(object : ConnectedCallback {
            override fun connected() {
                notifyConnectionSuccess()
            }
        })
    }

    fun registerDisconnectCallback(callback: Callback) {
        this.disconnectCallback = callback
        BluetoothSerialService.registerClosedCallback(object : ClosedCallback {
            override fun closed() {
                notifyConnectionLost()
            }
        })
    }

    fun registerOnDataCallback(callback: Callback) {
        this.dataAvailableCallback = callback
        BluetoothSerialService.registerDataCallback(object : DataCallback {
            override fun onData(data: ByteArray) {
                val data1 = data.toString(UTF_8)
                if (data1.isNotEmpty()) {
                    dataAvailableCallback?.run {
                        val nativeDataMessage =
                            NativeDataMessage(
                                this,
                                data1
                            )
                        messageHandler?.sendMessage(nativeDataMessage)
                    }
                }
            }
        })
    }

    fun getAddress(successCallback: Callback?, failureCallback: Callback?) {
        val macAddress = getBluetoothMacAddress()

        macAddress?.run {
            sendSuccess(successCallback)
        } ?: sendFailure(failureCallback, Exception("Unable to determine Bluetooth MAC address"))
    }

    private fun notifyConnectionSuccess() {
        connectCallback?.run {
            val nativeDataMessage =
                NativeDataMessage(
                    this,
                    null
                )
            messageHandler?.sendMessage(
                    nativeDataMessage
            )
        }
    }

    private fun notifyConnectionLost() {
        disconnectCallback?.run {
            val nativeDataMessage =
                NativeDataMessage(
                    this,
                    null
                )
            messageHandler?.sendMessage(
                    nativeDataMessage
            )
        }
    }

    private fun getBluetoothMacAddress(): String? {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
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
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!bluetoothAdapter.isEnabled) {
            bluetoothAdapter.enable()
        }
    }

    interface MessageHandler {
        fun sendMessage(message: NativeDataMessage)
    }
}
