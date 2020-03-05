package model

import kotlinx.serialization.*
import kotlinx.serialization.json.*

import com.fivestars.cordovaalternativepattern.model.Action

@Serializable
data class JavascriptMessage(val action: Action, val data: Map<String, String>?)