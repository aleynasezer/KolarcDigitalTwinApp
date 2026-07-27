package com.kolarc.digitaltwin.data.repository

import com.kolarc.digitaltwin.domain.model.DashboardSummary
import com.kolarc.digitaltwin.domain.model.SensorReading
import com.kolarc.digitaltwin.domain.repository.DashboardRepository
import kotlinx.coroutines.delay

class FakeDashboardRepository : DashboardRepository {

    override suspend fun getDashboardSummary(): DashboardSummary {
        delay(500)

        return DashboardSummary(
            onlineMachineCount = 4,
            offlineMachineCount = 1,
            averageOee = 89.4,
            activeAlarmCount = 3,
            sensorReadings = listOf(
                SensorReading(
                    label = "08:00",
                    temperature = 42.0,
                    vibration = 1.8,
                    energyConsumption = 12.4
                ),
                SensorReading(
                    label = "10:00",
                    temperature = 46.5,
                    vibration = 2.1,
                    energyConsumption = 14.8
                ),
                SensorReading(
                    label = "12:00",
                    temperature = 49.2,
                    vibration = 2.7,
                    energyConsumption = 16.3
                ),
                SensorReading(
                    label = "14:00",
                    temperature = 47.8,
                    vibration = 2.3,
                    energyConsumption = 15.1
                ),
                SensorReading(
                    label = "16:00",
                    temperature = 44.6,
                    vibration = 1.9,
                    energyConsumption = 13.7
                )
            )
        )
    }
}