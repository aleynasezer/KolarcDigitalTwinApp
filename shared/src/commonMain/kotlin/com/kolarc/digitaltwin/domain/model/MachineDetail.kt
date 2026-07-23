package com.kolarc.digitaltwin.domain.model

data class MachineDetail(
    val id: String,
    val location: String,
    val model: String,
    val name: String,
    val serialNumber: String,
    val isOnline: Boolean,
    val lastConnected: String,
    val oeePercentage: Double,
    val workingHours: Int,
    val lastMaintenanceDate: String
)