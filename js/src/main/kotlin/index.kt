package com.fivestars.cordovaalternativepattern

import org.w3c.dom.MessageChannel
import org.w3c.dom.MessageEvent
import kotlin.browser.window

val channel = MessageChannel()
var jsPortOne = channel.port1
var jsPortTwo = channel.port2

fun configureChannel() {


    window.addEventListener("message", {
    }, false)

    jsPortOne.addEventListener("message", {
        window.alert((it as MessageEvent).data.toString())
    }, false)

    jsPortTwo.addEventListener("message", {
        window.alert((it as MessageEvent).data.toString())
    }, false)

    jsPortOne.start()
    jsPortTwo.start()
}