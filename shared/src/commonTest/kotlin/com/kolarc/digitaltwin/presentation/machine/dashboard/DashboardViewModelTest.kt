package com.kolarc.digitaltwin.presentation.dashboard

import com.kolarc.digitaltwin.domain.model.DashboardSummary
import com.kolarc.digitaltwin.domain.model.SensorReading
import com.kolarc.digitaltwin.domain.repository.DashboardRepository
import com.kolarc.digitaltwin.domain.usecase.GetDashboardSummaryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun dashboardSummarySuccessfullyLoaded() = runTest(dispatcher) {

        val repository = FakeDashboardRepository()

        val viewModel = DashboardViewModel(
            getDashboardSummaryUseCase =
                GetDashboardSummaryUseCase(repository)
        )

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertNotNull(state.summary)

        assertEquals(
            4,
            state.summary.onlineMachineCount
        )

        assertEquals(
            89.4,
            state.summary.averageOee
        )
    }

    private class FakeDashboardRepository :
        DashboardRepository {

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
                    )
                )
            )
        }
    }
}