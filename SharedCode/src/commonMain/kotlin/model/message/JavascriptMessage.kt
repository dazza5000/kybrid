package com.fivestars.cordovaalternativepattern.model.message

import com.fivestars.cordovaalternativepattern.model.Action
import com.fivestars.cordovaalternativepattern.model.Callback
import kotlinx.serialization.Serializable

@Serializable
data class JavascriptMessage(
    val action: Action,
    val successCallback: Callback?,
    val failureCallback: Callback?,
    val data: Map<String, String>? = null
)