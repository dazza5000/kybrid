package com.fivestars.cordovaalternativepattern


expect fun platformName(): String

fun createApplicationScreenMessage() : String {
    return "Kotlin Rocks on ${platformName()}"
}

const val KEY_MAC_ADDRESS = "macAddress"
const val KEY_SEND_DATA = "sendData"
const val KEY_RECEIVE_DATA = "receiveData"
const val KEY_CAPTURE_PORT = "capturePort"