package com.fivestars.cordovaalternativepattern.model.message

import com.fivestars.cordovaalternativepattern.model.Callback
import kotlinx.serialization.Serializable

@Serializable
class NativeDataMessage(val callback: Callback, val data: String?)