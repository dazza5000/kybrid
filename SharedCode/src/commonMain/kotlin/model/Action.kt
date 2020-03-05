package com.fivestars.cordovaalternativepattern.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
enum class Action {
    CONNECT,
    DISCONNECT,
    SEND,
    LISTEN,
    GET_ADDRESS,
    REGISTER_DATA_CALLBACK,
    REGISTER_CONNECT_CALLBACK,
    REGISTER_CLOSE_CALLBACK
}