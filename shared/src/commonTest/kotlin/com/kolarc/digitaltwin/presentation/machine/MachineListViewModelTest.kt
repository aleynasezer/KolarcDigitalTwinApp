package com.kolarc.digitaltwin.presentation.machine

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import com.kolarc.digitaltwin.domain.usecase.GetMachinesUseCase
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MachineListViewModelTest {

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
    fun viewModelLoadsMachinesSuccessfully() = runTest(testDispatcher) {
        val machines = listOf(
            createMachine(
                id = "1",
                name = "Kaynak Makinesi"
            )
        )

        val repository = FakeMachineRepository(
            machines = machines
        )

        val viewModel = MachineListViewModel(
            getMachinesUseCase = GetMachinesUseCase(repository)
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals(
            expected = machines,
            actual = state.machines
        )
        assertFalse(state.isLoading)
        assertEquals(
            expected = null,
            actual = state.errorMessage
        )
    }

    @Test
    fun searchQueryIsUpdated() = runTest(testDispatcher) {
        val viewModel = MachineListViewModel(
            getMachinesUseCase = GetMachinesUseCase(
                FakeMachineRepository()
            )
        )

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onSearchQueryChanged("Kaynak")

        assertEquals(
            expected = "Kaynak",
            actual = viewModel.uiState.value.searchQuery
        )
    }

    @Test
    fun validTabIsSelected() = runTest(testDispatcher) {
        val viewModel = MachineListViewModel(
            getMachinesUseCase = GetMachinesUseCase(
                FakeMachineRepository()
            )
        )

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onTabSelected(
            MachineListUiState.LIVE_VIEW_TAB_INDEX
        )

        assertEquals(
            expected = MachineListUiState.LIVE_VIEW_TAB_INDEX,
            actual = viewModel.uiState.value.selectedTab
        )
    }

    @Test
    fun invalidTabIsIgnored() = runTest(testDispatcher) {
        val viewModel = MachineListViewModel(
            getMachinesUseCase = GetMachinesUseCase(
                FakeMachineRepository()
            )
        )

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onTabSelected(99)

        assertEquals(
            expected = MachineListUiState.OVERVIEW_TAB_INDEX,
            actual = viewModel.uiState.value.selectedTab
        )
    }

    @Test
    fun errorStateIsShownWhenLoadingFails() = runTest(testDispatcher) {
        val viewModel = MachineListViewModel(
            getMachinesUseCase = GetMachinesUseCase(
                FakeMachineRepository(
                    shouldThrowError = true
                )
            )
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state.machines.isEmpty())
        assertFalse(state.isLoading)
        assertEquals(
            expected = "Test error",
            actual = state.errorMessage
        )
    }

    private fun createMachine(
        id: String,
        name: String
    ): MachineStatus {
        return MachineStatus(
            id = id,
            location = "Test Location",
            model = "Test Model",
            name = name,
            serialNumber = "TEST-$id",
            isOnline = true,
            lastConnected = "15/07/2026 12:00:00"
        )
    }

    private class FakeMachineRepository(
        private val machines: List<MachineStatus> = emptyList(),
        private val shouldThrowError: Boolean = false
    ) : MachineRepository {

        override suspend fun getAllMachines(): List<MachineStatus> {
            if (shouldThrowError) {
                throw IllegalStateException("Test error")
            }

            return machines
        }

        override suspend fun getMachineStatus(
            machineId: String
        ): MachineStatus {
            return machines.firstOrNull { machine ->
                machine.id == machineId
            } ?: error("Machine not found: $machineId")
        }

        override suspend fun getMachineDetail(
            machineId: String
        ): MachineDetail {
            error("getMachineDetail is not required for this test")
        }

        override fun getWeldRecords(
            type: String
        ): List<WeldRecord> {
            return emptyList()
        }
    }
}