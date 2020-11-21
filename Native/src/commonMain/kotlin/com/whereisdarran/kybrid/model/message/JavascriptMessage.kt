package com.whereisdarran.kybrid.model.message

import kotlinx.serialization.Serializable

@Serializable
data class JavascriptMessage<Action>(
    val action: Action,
    val data: String?,
    val callbackUid: String?
)
