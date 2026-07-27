package com.kolarc.digitaltwin.domain.model

data class DashboardSummary(
    val onlineMachineCount: Int,
    val offlineMachineCount: Int,
    val averageOee: Double,
    val activeAlarmCount: Int,
    val sensorReadings: List<SensorReading>
)

data class SensorReading(
    val label: String,
    val temperature: Double,
    val vibration: Double,
    val energyConsumption: Double
)