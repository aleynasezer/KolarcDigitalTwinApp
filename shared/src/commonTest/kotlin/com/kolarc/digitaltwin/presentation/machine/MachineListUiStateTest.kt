package com.kolarc.digitaltwin.presentation.machine

import com.kolarc.digitaltwin.domain.model.MachineStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class MachineListUiStateTest {

    @Test
    fun `filteredMachines returns all machines when search query is blank`() {
        val machines = listOf(
            createMachine(
                id = "1",
                name = "Kaynak Makinesi 1"
            ),
            createMachine(
                id = "2",
                name = "Kaynak Makinesi 2"
            )
        )

        val uiState = MachineListUiState(
            machines = machines,
            searchQuery = ""
        )

        assertEquals(
            expected = machines,
            actual = uiState.filteredMachines
        )
    }

    @Test
    fun `filteredMachines returns machines matching search query`() {
        val expectedMachine = createMachine(
            id = "1",
            name = "Kaynak Makinesi"
        )

        val uiState = MachineListUiState(
            machines = listOf(
                expectedMachine,
                createMachine(
                    id = "2",
                    name = "Kesim Makinesi"
                )
            ),
            searchQuery = "Kaynak"
        )

        assertEquals(
            expected = listOf(expectedMachine),
            actual = uiState.filteredMachines
        )
    }

    @Test
    fun `filteredMachines ignores letter case`() {
        val expectedMachine = createMachine(
            id = "1",
            name = "Kaynak Makinesi"
        )

        val uiState = MachineListUiState(
            machines = listOf(expectedMachine),
            searchQuery = "kaynak"
        )

        assertEquals(
            expected = listOf(expectedMachine),
            actual = uiState.filteredMachines
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
            lastConnected = "11/07/2026 12:00:00"
        )
    }
}