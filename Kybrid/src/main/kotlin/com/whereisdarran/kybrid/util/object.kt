package com.whereisdarran.bluetooth.com.whereisdarran.kybrid.util

@ExperimentalStdlibApi
fun <T> createKotlinMapFromObject(jsObject: dynamic): Map<String, T> = buildMap {
    (js("Object.keys") as (dynamic) -> Array<String>)
        .invoke(jsObject)
        .forEach { key ->
            put(key, jsObject[key] as T)
        }
}
