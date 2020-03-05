package com.fivestars.cordovaalternativepattern


expect fun platformName(): String

fun createApplicationScreenMessage() : String {
    return "Kotlin Rocks on ${platformName()}"
}

const val KEY_MAC_ADDRESS = "macAddress"