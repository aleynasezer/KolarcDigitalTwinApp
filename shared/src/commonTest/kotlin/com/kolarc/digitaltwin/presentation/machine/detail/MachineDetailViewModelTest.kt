package com.kolarc.digitaltwin.presentation.machine.detail

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import com.kolarc.digitaltwin.domain.usecase.GetMachineDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MachineDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun machineDetailLoadsSuccessfully() = runTest(testDispatcher) {
        val repository = FakeMachineRepository()

        val viewModel = MachineDetailViewModel(
            machineId = "1",
            getMachineDetailUseCase = GetMachineDetailUseCase(repository)
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertEquals(null, state.errorMessage)
        assertNotNull(state.machineDetail)
        assertEquals("1", state.machineDetail?.id)
        assertEquals("PRES KAYNAGI", state.machineDetail?.name)
        assertEquals(92.4, state.machineDetail?.oeePercentage)
    }

    @Test
    fun errorStateIsShownWhenLoadingFails() = runTest(testDispatcher) {
        val repository = FakeMachineRepository(
            shouldThrowError = true
        )

        val viewModel = MachineDetailViewModel(
            machineId = "1",
            getMachineDetailUseCase = GetMachineDetailUseCase(repository)
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state.machineDetail == null)
        assertFalse(state.isLoading)
        assertEquals("Test error", state.errorMessage)
    }

    private class FakeMachineRepository(
        private val shouldThrowError: Boolean = false
    ) : MachineRepository {

        override suspend fun getMachineDetail(
            machineId: String
        ): MachineDetail {
            if (shouldThrowError) {
                throw IllegalStateException("Test error")
            }

            return MachineDetail(
                id = machineId,
                location = "hol 3",
                model = "MX500",
                name = "PRES KAYNAGI",
                serialNumber = "127448",
                isOnline = true,
                lastConnected = "10/12/2025 08:07:33",
                oeePercentage = 92.4,
                workingHours = 4821,
                lastMaintenanceDate = "01/07/2026"
            )
        }

        override suspend fun getMachineStatus(
            machineId: String
        ): MachineStatus {
            error("Not required for this test")
        }

        override suspend fun getAllMachines(): List<MachineStatus> {
            return emptyList()
        }

        override fun getWeldRecords(
            type: String
        ): List<WeldRecord> {
            return emptyList()
        }
    }
}