package com.whereisdarran.instore.message

import kotlinx.serialization.Serializable

@Serializable
data class JavascriptMessage<Action>(
    val action: Action,
    val data: String?,
    val callbackUid: String?
)
