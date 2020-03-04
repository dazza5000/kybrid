package com.fivestars.cordovaalternativepattern

import model.JavascriptMessage

object BluetoothSerial {
    fun connect(macAddress: String, success: () -> Unit, failure: () -> Unit) {
        val message = JavascriptMessage(action = "connect", data = mapOf("macAddress" to macAddress))
        jsPortTwo.postMessage(message)
    }
}