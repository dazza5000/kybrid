package com.whereisdarran.instore.message

import kotlinx.serialization.Serializable

@Serializable
data class NativeMessage<Action>(
    val action: Action,
    val success: Boolean,
    val data: String?,
    val callbackUid: String?
)
