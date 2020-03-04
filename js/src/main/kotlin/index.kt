package com.fivestars.cordovaalternativepattern

import org.w3c.dom.MessageChannel
import org.w3c.dom.MessageEvent
import kotlin.browser.window

val channel = MessageChannel()
var incomingPort = channel.port1
var outputPort = channel.port2

fun configureChannel() {


    window.addEventListener("message", {
    }, false)

    incomingPort.addEventListener("message", {
        window.alert((it as MessageEvent).data.toString())
    }, false)

    outputPort.addEventListener("message", {
        window.alert((it as MessageEvent).data.toString())
    }, false)

    incomingPort.start()
    outputPort.start()
}