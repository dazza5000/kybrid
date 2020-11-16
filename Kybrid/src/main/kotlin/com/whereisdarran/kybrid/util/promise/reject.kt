package com.whereisdarran.kybrid.util.promise

fun rejectHelper(promiseReject: (Error) -> Unit): (dynamic) -> Unit {
    return fun(it: dynamic) {
        when (jsTypeOf(it)) {
            "string" -> promiseReject(Error(it as String))
            else -> promiseReject(it as Error)
        }
    }
}
