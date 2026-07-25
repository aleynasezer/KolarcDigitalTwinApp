package com.kolarc.digitaltwin.data.repository

import com.kolarc.digitaltwin.data.remote.MachineRemoteDataSource
import com.kolarc.digitaltwin.domain.model.MachineDetail
import com.kolarc.digitaltwin.domain.model.MachineStatus
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MachineRepositoryImplTest {

    @Test
    fun getAllMachinesReturnsMachinesFromRemoteDataSource() = runTest {
        val remoteDataSource = FakeRemoteDataSource()
        val repository = MachineRepositoryImpl(
            remoteDataSource = remoteDataSource
        )

        val result = repository.getAllMachines()

        assertEquals(
            expected = 2,
            actual = result.size
        )
        assertEquals(
            expected = "1",
            actual = result.first().id
        )
    }

    @Test
    fun getMachineDetailReturnsRequestedMachineDetail() = runTest {
        val remoteDataSource = FakeRemoteDataSource()
        val repository = MachineRepositoryImpl(
            remoteDataSource = remoteDataSource
        )

        val result = repository.getMachineDetail(
            machineId = "2"
        )

        assertEquals(
            expected = "2",
            actual = result.id
        )
        assertEquals(
            expected = "Machine 2",
            actual = result.name
        )
    }

    @Test
    fun getMachineStatusReturnsRequestedMachineFromList() = runTest {
        val remoteDataSource = FakeRemoteDataSource()
        val repository = MachineRepositoryImpl(
            remoteDataSource = remoteDataSource
        )

        val result = repository.getMachineStatus(
            machineId = "2"
        )

        assertEquals(
            expected = "2",
            actual = result.id
        )
        assertEquals(
            expected = "Machine 2",
            actual = result.name
        )
    }

    @Test
    fun getMachineStatusThrowsWhenMachineDoesNotExist() = runTest {
        val remoteDataSource = FakeRemoteDataSource()
        val repository = MachineRepositoryImpl(
            remoteDataSource = remoteDataSource
        )

        assertFailsWith<NoSuchElementException> {
            repository.getMachineStatus(
                machineId = "404"
            )
        }
    }

    private class FakeRemoteDataSource : MachineRemoteDataSource {

        private val machines = listOf(
            MachineStatus(
                id = "1",
                name = "Machine 1",
                model = "MX100",
                serialNumber = "SN-001",
                location = "Hol 1",
                isOnline = true,
                lastConnected = "24/07/2026 10:00"
            ),
            MachineStatus(
                id = "2",
                name = "Machine 2",
                model = "MX200",
                serialNumber = "SN-002",
                location = "Hol 2",
                isOnline = false,
                lastConnected = "24/07/2026 09:00"
            )
        )

        override suspend fun getAllMachines(): List<MachineStatus> {
            return machines
        }

        override suspend fun getMachineDetail(
            machineId: String
        ): MachineDetail {
            return MachineDetail(
                id = machineId,
                name = "Machine $machineId",
                model = "MX200",
                serialNumber = "SN-00$machineId",
                location = "Hol $machineId",
                isOnline = true,
                lastConnected = "24/07/2026 10:00",
                oeePercentage = 90.0,
                workingHours = 1000,
                lastMaintenanceDate = "20/07/2026"
            )
        }
    }
}