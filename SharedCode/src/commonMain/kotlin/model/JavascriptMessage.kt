package model

import kotlinx.serialization.*

import com.fivestars.cordovaalternativepattern.model.Action
import com.fivestars.cordovaalternativepattern.model.CallbackId

@Serializable
data class JavascriptMessage(val action: Action, val successCallbackId: CallbackId?, val failureCallbackId: CallbackId?, val data: Map<String, String>?)