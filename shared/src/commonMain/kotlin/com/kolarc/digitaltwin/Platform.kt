package com.kolarc.digitaltwin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform