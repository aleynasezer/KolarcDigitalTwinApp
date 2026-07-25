package com.kolarc.digitaltwin.domain.usecase

import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import com.kolarc.digitaltwin.domain.model.WeldRecord
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetMachineStatusUseCaseTest {

    @Test
    fun execute_yuklenen_makine_verilerini_dogru_sekilde_getirmeli() = runTest {
        val repository = FakeMachineRepository()
        val useCase = GetMachineStatusUseCase(repository)
        val testMachineId = "1"

        val result = useCase.execute(testMachineId)

        assertEquals(
            expected = "1",
            actual = result.id
        )
        assertEquals(
            expected = "MX500",
            actual = result.model
        )
        assertEquals(
            expected = "PRES KAYNAGI",
            actual = result.name
        )
        assertEquals(
            expected = "127448",
            actual = result.serialNumber
        )
        assertTrue(result.isOnline)
        assertEquals(
            expected = "10/12/2025 08:07:33",
            actual = result.lastConnected
        )
    }

    private class FakeMachineRepository : MachineRepository {

        override suspend fun getMachineStatus(
            machineId: String
        ): MachineStatus {
            return MachineStatus(
                id = machineId,
                location = "hol 3",
                model = "MX500",
                name = "PRES KAYNAGI",
                serialNumber = "127448",
                isOnline = true,
                lastConnected = "10/12/2025 08:07:33"
            )
        }

        override suspend fun getMachineDetail(
            machineId: String
        ): MachineDetail {
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