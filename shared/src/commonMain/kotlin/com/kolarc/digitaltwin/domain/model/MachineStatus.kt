package com.kolarc.digitaltwin.domain.model

// 1. Makine Durum Modeli
data class MachineStatus(
    val id: String,
    val location: String,       // Konumu
    val model: String,          // Modeli
    val name: String,           // Adı / Kime ait olduğu
    val serialNumber: String,   // Seri No
    val isOnline: Boolean,      // Bağlantı Durumu
    val lastConnected: String   // Son Bağlantı Zamanı
)

// 2. Kaynak Verileri Modeli (Domain Katmanında)
data class WeldRecord(
    val timestamp: String,
    val duration: String,
    val location: String,
    val serialNumber: String,
    val description: String,
    val organization: String,
    val limitViolation: String = "",
    val error: String = ""
)