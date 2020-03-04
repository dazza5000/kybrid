package model

import com.fivestars.cordovaalternativepattern.model.Action

data class JavascriptMessage(val action: Action, val data: Map<String, Any>?) {
}