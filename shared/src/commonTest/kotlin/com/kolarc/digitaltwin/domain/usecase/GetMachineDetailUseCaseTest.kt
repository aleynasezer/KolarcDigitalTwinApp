package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMachineDetailUseCaseTest {

    @Test
    fun `returns machine detail for requested machine id`() = runTest {
        val repository = FakeMachineRepository()
        val useCase = GetMachineDetailUseCase(repository)

        val result = useCase("1")

        assertEquals("1", result.id)
        assertEquals("PRES KAYNAGI", result.name)
        assertEquals(92.4, result.oeePercentage)
        assertEquals(4821, result.workingHours)
        assertEquals("01/07/2026", result.lastMaintenanceDate)
    }

    private class FakeMachineRepository : MachineRepository {

        override suspend fun getMachineDetail(
            machineId: String
        ): MachineDetail {
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