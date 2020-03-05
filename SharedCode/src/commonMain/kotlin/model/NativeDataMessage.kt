package com.fivestars.cordovaalternativepattern.model

import kotlinx.serialization.Serializable

@Serializable
class NativeDataMessage(val callbackId: CallbackId, val data: ByteArray?)