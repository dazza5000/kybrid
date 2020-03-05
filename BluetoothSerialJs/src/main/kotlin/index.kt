//package com.fivestars.cordovaalternativepattern

import org.w3c.dom.MessageChannel
import org.w3c.dom.MessageEvent
import kotlin.browser.window

val channel = MessageChannel()
var incomingPort = channel.port1
var outputPort = channel.port2

fun configureChannel() {
    console.log("Configuring channel")
    window.addEventListener("message", {
        val event = it as MessageEvent

        if (event.data != "capturePort") {
            incomingPort.postMessage(event.data)
        } else if (event.data == "capturePort") {
            console.log("assigning captured port")
                outputPort = event.ports[0]
        }
    }, false)

    incomingPort.start()
    outputPort.start()
}

fun postAlert() {
    window.alert("yolo")
}