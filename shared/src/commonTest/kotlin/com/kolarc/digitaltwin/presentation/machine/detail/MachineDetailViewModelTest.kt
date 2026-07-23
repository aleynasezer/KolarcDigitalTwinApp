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
import kotlin.test.assertNull
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
            machineId = MACHINE_ID,
            getMachineDetailUseCase = GetMachineDetailUseCase(repository)
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
        assertNotNull(state.machineDetail)
        assertEquals(MACHINE_ID, state.machineDetail?.id)
        assertEquals("PRES KAYNAGI", state.machineDetail?.name)
        assertEquals(92.4, state.machineDetail?.oeePercentage)
    }

    @Test
    fun errorStateIsShownWhenLoadingFails() = runTest(testDispatcher) {
        val repository = FakeMachineRepository(
            shouldAlwaysThrowError = true
        )

        val viewModel = MachineDetailViewModel(
            machineId = MACHINE_ID,
            getMachineDetailUseCase = GetMachineDetailUseCase(repository)
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertNull(state.machineDetail)
        assertFalse(state.isLoading)
        assertEquals(ERROR_MESSAGE, state.errorMessage)
    }

    @Test
    fun retryLoadsMachineDetailAfterFirstFailure() = runTest(testDispatcher) {
        val repository = FakeMachineRepository(
            shouldFailFirstRequest = true
        )

        val viewModel = MachineDetailViewModel(
            machineId = MACHINE_ID,
            getMachineDetailUseCase = GetMachineDetailUseCase(repository)
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val errorState = viewModel.uiState.value

        assertFalse(errorState.isLoading)
        assertNull(errorState.machineDetail)
        assertEquals(ERROR_MESSAGE, errorState.errorMessage)

        viewModel.retry()

        testDispatcher.scheduler.advanceUntilIdle()

        val successState = viewModel.uiState.value

        assertFalse(successState.isLoading)
        assertNull(successState.errorMessage)
        assertNotNull(successState.machineDetail)
        assertEquals(
            expected = MACHINE_ID,
            actual = successState.machineDetail?.id
        )
        assertEquals(
            expected = 2,
            actual = repository.requestCount
        )
    }

    private class FakeMachineRepository(
        private val shouldAlwaysThrowError: Boolean = false,
        private val shouldFailFirstRequest: Boolean = false
    ) : MachineRepository {

        var requestCount: Int = 0
            private set

        override suspend fun getMachineDetail(
            machineId: String
        ): MachineDetail {
            requestCount++

            val shouldThrowError =
                shouldAlwaysThrowError ||
                        (shouldFailFirstRequest && requestCount == 1)

            if (shouldThrowError) {
                throw IllegalStateException(ERROR_MESSAGE)
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

    private companion object {
        const val MACHINE_ID = "1"
        const val ERROR_MESSAGE = "Test error"
    }
}