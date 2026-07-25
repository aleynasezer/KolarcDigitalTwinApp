package com.kolarc.digitaltwin.data.mapper

import com.kolarc.digitaltwin.data.remote.dto.MachineStatusDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class MachineStatusMapperTest {

    @Test
    fun machineStatusDtoMapsToDomainCorrectly() {
        val dto = MachineStatusDto(
            id = "1",
            name = "PRES KAYNAGI",
            model = "MX500",
            serialNumber = "127448",
            location = "Hol 3",
            isOnline = false,
            lastConnected = "10/12/2025 08:07:33"
        )

        val result = dto.toDomain()

        assertEquals(
            expected = "1",
            actual = result.id
        )
        assertEquals(
            expected = "PRES KAYNAGI",
            actual = result.name
        )
        assertEquals(
            expected = "MX500",
            actual = result.model
        )
        assertEquals(
            expected = "127448",
            actual = result.serialNumber
        )
        assertEquals(
            expected = "Hol 3",
            actual = result.location
        )
        assertFalse(result.isOnline)
        assertEquals(
            expected = "10/12/2025 08:07:33",
            actual = result.lastConnected
        )
    }
}