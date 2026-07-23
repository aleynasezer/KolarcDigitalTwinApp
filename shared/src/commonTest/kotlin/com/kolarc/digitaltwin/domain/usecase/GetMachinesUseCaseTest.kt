package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetMachinesUseCaseTest {

    @Test
    fun `returns all machines from repository`() = runTest {
        val repository = FakeMachineRepository()
        val useCase = GetMachinesUseCase(repository)

        val result = useCase()

        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("PRES KAYNAGI", result[0].name)
        assertEquals("2", result[1].id)
        assertEquals("Utest -3", result[1].name)
    }

    @Test
    fun `returns empty list when repository has no machines`() = runTest {
        val repository = FakeMachineRepository(
            machines = emptyList()
        )
        val useCase = GetMachinesUseCase(repository)

        val result = useCase()

        assertTrue(result.isEmpty())
    }

    private class FakeMachineRepository(
        private val machines: List<MachineStatus> = listOf(
            MachineStatus(
                id = "1",
                location = "hol 3",
                model = "MX500",
                name = "PRES KAYNAGI",
                serialNumber = "127448",
                isOnline = true,
                lastConnected = "10/12/2025 08:07:33"
            ),
            MachineStatus(
                id = "2",
                location = "Ankara",
                model = "MX500",
                name = "Utest -3",
                serialNumber = "131131",
                isOnline = false,
                lastConnected = "07/07/2026 15:33:12"
            )
        )
    ) : MachineRepository {

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

        override suspend fun getAllMachines(): List<MachineStatus> {
            return machines
        }

        override fun getWeldRecords(
            type: String
        ): List<WeldRecord> {
            return emptyList()
        }
    }
}