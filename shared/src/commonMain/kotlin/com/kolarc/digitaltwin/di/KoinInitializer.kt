package com.kolarc.digitaltwin.di

import org.koin.core.Koin
import org.koin.core.context.startKoin

object KoinProvider {

    private var koinInstance: Koin? = null

    val koin: Koin
        get() = checkNotNull(koinInstance) {
            "Koin henüz başlatılmadı. Önce initializeKoin() çağrılmalıdır."
        }

    fun initialize() {
        if (koinInstance != null) {
            return
        }

        koinInstance = startKoin {
            modules(appModule)
        }.koin
    }
}

fun initializeKoin() {
    KoinProvider.initialize()
}