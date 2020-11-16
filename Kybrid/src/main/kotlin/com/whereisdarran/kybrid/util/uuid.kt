package com.whereisdarran.instore.util

import kotlin.random.Random
import kotlin.random.nextUBytes

@ExperimentalUnsignedTypes
fun uuid(): String = Random.Default.nextUBytes(16).contentToString()
