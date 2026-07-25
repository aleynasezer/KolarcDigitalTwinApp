package com.kolarc.digitaltwin.data.remote

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MachineApiServiceTest {

    @Test
    fun getMachinesReturnsMachinesFromMockApi() = runTest {
        val client = createMockHttpClient()
        val apiService = MachineApiService(client)

        val result = apiService.getMachines()

        assertEquals(
            expected = 5,
            actual = result.size
        )

        val firstMachine = result.first()

        assertEquals(
            expected = "1",
            actual = firstMachine.id
        )

        assertEquals(
            expected = "PRES KAYNAGI",
            actual = firstMachine.name
        )

        assertTrue(firstMachine.isOnline)

        val secondMachine = result[1]

        assertEquals(
            expected = "Utest -3",
            actual = secondMachine.name
        )

        assertFalse(secondMachine.isOnline)

        client.close()
    }

    @Test
    fun getMachineDetailReturnsMachineDetailFromMockApi() = runTest {
        val client = createMockHttpClient()
        val apiService = MachineApiService(client)

        val result = apiService.getMachineDetail(
            machineId = "2"
        )

        assertEquals(
            expected = "2",
            actual = result.id
        )

        assertEquals(
            expected = "Utest -3",
            actual = result.name
        )

        assertEquals(
            expected = "MX500",
            actual = result.model
        )

        assertEquals(
            expected = "131131",
            actual = result.serialNumber
        )

        assertEquals(
            expected = "Ankara",
            actual = result.location
        )

        assertFalse(result.isOnline)

        assertEquals(
            expected = 78.1,
            actual = result.oeePercentage
        )

        assertEquals(
            expected = 2960,
            actual = result.workingHours
        )

        assertEquals(
            expected = "25/06/2026",
            actual = result.lastMaintenanceDate
        )

        client.close()
    }
}