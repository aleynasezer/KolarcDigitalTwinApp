package com.kolarc.digitaltwin.data.mapper

import com.kolarc.digitaltwin.data.remote.dto.MachineDetailDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class MachineDetailMapperTest {

    @Test
    fun machineDetailDtoMapsToDomainCorrectly() {
        val dto = MachineDetailDto(
            id = "2",
            name = "UTEST-3",
            model = "UT300",
            serialNumber = "127449",
            location = "Hol 1",
            isOnline = false,
            lastConnected = "09/12/2025 17:40:11",
            oeePercentage = 78.1,
            workingHours = 2960,
            lastMaintenanceDate = "25/06/2026"
        )

        val result = dto.toDomain()

        assertEquals(
            expected = "2",
            actual = result.id
        )
        assertEquals(
            expected = "UTEST-3",
            actual = result.name
        )
        assertEquals(
            expected = "UT300",
            actual = result.model
        )
        assertEquals(
            expected = "127449",
            actual = result.serialNumber
        )
        assertEquals(
            expected = "Hol 1",
            actual = result.location
        )
        assertFalse(result.isOnline)
        assertEquals(
            expected = "09/12/2025 17:40:11",
            actual = result.lastConnected
        )
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
    }
}