package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.DashboardSummary
import com.kolarc.digitaltwin.domain.model.SensorReading
import com.kolarc.digitaltwin.domain.repository.DashboardRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDashboardSummaryUseCaseTest {

    @Test
    fun dashboardSummaryRepositorydenDogruSekildeGetirilir() = runTest {
        val repository = FakeDashboardRepository()
        val useCase = GetDashboardSummaryUseCase(
            repository = repository
        )

        val result = useCase()

        assertEquals(
            expected = 4,
            actual = result.onlineMachineCount
        )
        assertEquals(
            expected = 1,
            actual = result.offlineMachineCount
        )
        assertEquals(
            expected = 89.4,
            actual = result.averageOee
        )
        assertEquals(
            expected = 3,
            actual = result.activeAlarmCount
        )
        assertEquals(
            expected = 2,
            actual = result.sensorReadings.size
        )
    }

    private class FakeDashboardRepository : DashboardRepository {

        override suspend fun getDashboardSummary(): DashboardSummary {
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
                    )
                )
            )
        }
    }
}