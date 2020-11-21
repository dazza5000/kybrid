package com.whereisdarran.kybrid.model.message

import kotlinx.serialization.Serializable

@Serializable
data class PluginErrorMessage(
    val error: String,
    val stackTrace: String
)
