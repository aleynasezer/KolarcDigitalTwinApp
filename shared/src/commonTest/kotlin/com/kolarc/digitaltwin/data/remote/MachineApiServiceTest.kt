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
}